package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.req.BoardCreateRequestDto;
import com.boardwe.boardwe.dto.res.BoardCreateResponseDto;
import com.boardwe.boardwe.dto.req.inner.BoardThemeCreateRequestDto;
import com.boardwe.boardwe.dto.req.inner.MemoColorAndTextColorRequestDto;
import com.boardwe.boardwe.dto.req.inner.MemoImageAndTextColorRequestDto;
import com.boardwe.boardwe.entity.*;
import com.boardwe.boardwe.exception.custom.BoardThemeNotFoundException;
import com.boardwe.boardwe.exception.custom.CannotStoreFileException;
import com.boardwe.boardwe.exception.custom.UnableToCreateDirectoryException;
import com.boardwe.boardwe.repository.*;
import com.boardwe.boardwe.service.BoardCreateService;
import com.boardwe.boardwe.type.BackgroundType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardCreateServiceImpl implements BoardCreateService {

    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;
    private final BoardThemeRepository boardThemeRepository;
    private final ThemeCategoryRepository themeCategoryRepository;
    private final ImageInfoRepository imageInfoRepository;
    private final MemoThemeRepository memoThemeRepository;
    private final Path rootDir;

    private final String USER_THEME_NAME = "TEMP";

    @Override
    public BoardCreateResponseDto createBoard(BoardCreateRequestDto requestDto) {

        Long requestBoardThemeId = requestDto.getBoardThemeId();
        BoardTheme boardTheme;

        //테마가 기존 테마인지, 새로 만든 테마인지
        if (requestBoardThemeId == null) {
            ThemeCategory themeCategory = themeCategoryRepository.save(ThemeCategory.builder().name(USER_THEME_NAME).build());

            //테마 저장
            BoardThemeCreateRequestDto theme = requestDto.getTheme();
            if (theme.getBoardBackgroundImage() != null) {
                //이미지 저장

                String imageBase64 = theme.getBoardBackgroundImage();
                ImageInfo imageInfo = saveImageAndCreateImageInfo(imageBase64,theme.getBoardBackgroundImageName());


                boardTheme = boardThemeRepository.save(BoardTheme.builder()
                        .themeCategory(themeCategory)
                        .name(USER_THEME_NAME)
                        .backgroundType(BackgroundType.IMAGE)
                        .backgroundImageInfo(imageInfo)
                        .font(theme.getBoardFont()).build());
            } else {
                boardTheme = boardThemeRepository.save(BoardTheme.builder()
                        .themeCategory(themeCategory)
                        .name(USER_THEME_NAME)
                        .backgroundType(BackgroundType.COLOR)
                        .backgroundColor(theme.getBoardBackgroundColor())
                        .font(theme.getBoardFont()).build());
            }
            for (MemoImageAndTextColorRequestDto imageAndTextColor : theme.getMemoImageTextColorSets()) {
                String imageBase64 = imageAndTextColor.getMemoBackgroundImage();
                ImageInfo imageInfo = saveImageAndCreateImageInfo(imageBase64,imageAndTextColor.getMemoBackgroundImageName());

                memoThemeRepository.save(MemoTheme.builder()
                        .boardTheme(boardTheme)
                        .backgroundType(BackgroundType.IMAGE)
                        .backgroundImageInfo(imageInfo)
                        .build());
            }
            for (MemoColorAndTextColorRequestDto backgroundTextColor : theme.getMemoBackgroundTextColorSets()) {
                memoThemeRepository.save(MemoTheme.builder()
                        .boardTheme(boardTheme)
                        .backgroundType(BackgroundType.COLOR)
                        .backgroundColor(backgroundTextColor.getMemoBackgroundColor())
                        .textColor(backgroundTextColor.getMemoTextColor())
                        .build());

            }

        } else {
            boardTheme = boardThemeRepository.findById(requestBoardThemeId)
                    .orElseThrow(BoardThemeNotFoundException::new);
        }

        // 보드 생성
        String boardUuid = UUID.randomUUID().toString();
        Board board = Board.builder()
                .boardTheme(boardTheme)
                .name(requestDto.getBoardName())
                .description(requestDto.getBoardDescription())
                .code(boardUuid)
                .writingStartTime(requestDto.getWritingStartTime())
                .writingEndTime(requestDto.getWritingEndTime())
                .openStartTime(requestDto.getOpenStartTime())
                .openEndTime(requestDto.getOpenEndTime())
                .password(requestDto.getPassword())
                .openType(requestDto.getOpenType())
                .views(0).build();

        boardRepository.save(board);
        // 태그 저장
        List<String> tags = requestDto.getTags();

        for (String tag : tags) {
            tagRepository.save(Tag.builder().board(board).value(tag).build());
        }

        return BoardCreateResponseDto.builder().boardLink(String.format("/board/%s/welcome", boardUuid)).build();
    }

    private ImageInfo saveImageAndCreateImageInfo(String base64, String fileName) {
        String imageUuid = UUID.randomUUID().toString();
        String imageOriginalName = fileName.substring(0, fileName.lastIndexOf("."));
        String imageExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String imageSavedName = String.format("%s.%s",imageUuid,imageExtension);

        //이미지 저장경로 정해야함
//        String imageDir = "/images";
        String imageDir = "";

        Path savedPath = getTargetPath(imageDir, imageSavedName);
        saveBase64File(base64, savedPath);

        return imageInfoRepository.save(ImageInfo.builder()
                .uuid(imageUuid)
                .originalName(imageOriginalName)
                .savedName(imageSavedName)
                .extension(imageExtension)
                .path(imageDir).build());

    }


    private Path getTargetPath(String savedDir, String savedName) {
        if (!StringUtils.hasText(savedDir)){
            return rootDir.resolve(savedName);
        }
        if (savedDir.startsWith("/")){
            savedDir = savedDir.substring(1);
        }
        if (!savedDir.endsWith("/")) {
            savedDir = savedDir + "/";
        }
        Path targetDir = rootDir.resolve(savedDir);
        createDirectory(targetDir);
        return targetDir.resolve(savedName);
    }

    private void createDirectory(Path targetDir) {
        try {
            Files.createDirectories(targetDir);
        } catch (IOException e) {
            throw new UnableToCreateDirectoryException();
        }
    }

    public void saveBase64File(String base64, Path savedPath) {
        File file = savedPath.toFile();
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            file.delete();
            throw new CannotStoreFileException();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
