package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.req.BoardCreateRequestDto;
import com.boardwe.boardwe.dto.req.BoardDeleteRequestDto;
import com.boardwe.boardwe.dto.req.inner.BoardThemeCreateRequestDto;
import com.boardwe.boardwe.dto.req.inner.MemoColorAndTextColorRequestDto;
import com.boardwe.boardwe.dto.req.inner.MemoImageAndTextColorRequestDto;
import com.boardwe.boardwe.dto.res.*;
import com.boardwe.boardwe.dto.res.inner.MemoSelectResponseDto;
import com.boardwe.boardwe.entity.*;
import com.boardwe.boardwe.exception.custom.*;
import com.boardwe.boardwe.repository.*;
import com.boardwe.boardwe.service.BoardService;
import com.boardwe.boardwe.type.BackgroundType;
import com.boardwe.boardwe.type.BoardStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;
    private final BoardThemeRepository boardThemeRepository;
    private final ThemeCategoryRepository themeCategoryRepository;
    private final ImageInfoRepository imageInfoRepository;
    private final MemoThemeRepository memoThemeRepository;
    private final MemoRepository memoRepository;
    private final Path rootDir;

    private final String USER_THEME_NAME = "TEMP";

    @Override
    @Transactional
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

    @Override
    public BoardReadResponseDto readBoard(String boardCode) {
        Board board = boardRepository.findByCode(boardCode)
                .orElseThrow(BoardNotFoundException::new);

        BoardStatus boardStatus = BoardStatus.calculateBoardStatus(
                board.getWritingStartTime(),
                board.getWritingEndTime(),
                board.getOpenStartTime(),
                board.getOpenEndTime());

        validateBoardStatus(boardStatus);

        return BoardReadResponseDto.builder()
                .boardName(board.getName())
                .boardDescription(board.getDescription())
                .writingStartTime(board.getWritingStartTime())
                .writingEndTime(board.getWritingEndTime())
                .openStartTime(board.getOpenStartTime())
                .openEndTime(board.getOpenEndTime())
                .openType(board.getOpenType())
                .boardStatus(boardStatus)
                .theme(getThemeResponseDto(board.getBoardTheme()))
                .memos(boardStatus == BoardStatus.OPEN? getMemoResponseDtos(board) : null)
                .build();
    }

    @Override
    @Transactional
    public void deleteBoard(BoardDeleteRequestDto requestDto, String boardCode) {
        Board board = boardRepository.findByCode(boardCode).orElseThrow(BoardNotFoundException::new);

        if(Objects.equals(board.getPassword(), requestDto.getPassword())){

            memoRepository.deleteByBoard(board);
            tagRepository.deleteByBoard(board);

            boardRepository.delete(board);
        }
    }

    @Override
    public WelcomeBoardResponseDto getBoardWelcomePage(String boardCode) {
        Board board = boardRepository.findByCode(boardCode).orElseThrow(BoardNotFoundException::new);
        BoardTheme boardTheme = board.getBoardTheme();
        BoardStatus boardStatus = BoardStatus.calculateBoardStatus(
                board.getWritingStartTime(),
                board.getWritingEndTime(),
                board.getOpenStartTime(),
                board.getOpenEndTime()
        );

        return WelcomeBoardResponseDto.builder()
                .boardName(board.getName())
                .boardDescription(board.getDescription())
                .tags(getTagValues(board.getId()))
                .memoCnt(getMemoCnt(board.getId()))
                .boardViews(board.getViews())
                .writingStartTime(board.getWritingStartTime())
                .writingEndTime(board.getWritingEndTime())
                .openStartTime(board.getOpenStartTime())
                .openEndTime(board.getOpenEndTime())
                .boardStatus(boardStatus)
                .boardBackgroundType(boardTheme.getBackgroundType())
                .boardBackground(
                        boardTheme.getBackgroundType() == BackgroundType.COLOR ?
                                boardTheme.getBackgroundColor() : getBackgroundImageUrl(boardTheme.getBackgroundImageInfo())
                )
                .boardFont(board.getBoardTheme().getFont())
                .build();
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

    private static void validateBoardStatus(BoardStatus boardStatus) {
        if (boardStatus == BoardStatus.BEFORE_WRITING) {
            throw new BoardBeforeWritingException();
        } else if (boardStatus == BoardStatus.BEFORE_OPEN) {
            throw new BoardBeforeOpenException();
        } else if (boardStatus == BoardStatus.CLOSED) {
            throw new BoardClosedException();
        }
    }

    private List<MemoSelectResponseDto> getMemoResponseDtos(Board board) {
        List<Memo> memos = memoRepository.findByBoardId(board.getId());
        List<MemoSelectResponseDto> memoResponseDtos = new ArrayList<>();
        for (Memo memo : memos) {
            memoResponseDtos.add(MemoSelectResponseDto.builder()
                    .memoThemeId(memo.getMemoTheme().getId())
                    .memoContent(memo.getContent())
                    .build());
        }
        return memoResponseDtos;
    }

    private BoardThemeSelectResponseDto getThemeResponseDto(BoardTheme boardTheme) {
        String boardBackgroundValue = (boardTheme.getBackgroundType() == BackgroundType.COLOR) ?
                boardTheme.getBackgroundColor() : getBackgroundImageUrl(boardTheme.getBackgroundImageInfo());

        return BoardThemeSelectResponseDto.builder()
                .boardBackgroundType(boardTheme.getBackgroundType())
                .boardBackground(boardBackgroundValue)
                .boardFont(boardTheme.getFont())
                .memoThemes(getMemoThemeResponseDtos(boardTheme))
                .build();
    }

    private List<MemoThemeSelectResponseDto> getMemoThemeResponseDtos(BoardTheme boardTheme) {
        List<MemoTheme> memoThemes = memoThemeRepository.findByBoardThemeId(boardTheme.getId());
        List<MemoThemeSelectResponseDto> memoThemeSelectResponseDtos = new ArrayList<>();
        for (MemoTheme memoTheme : memoThemes) {
            String memoBackgroundValue = (memoTheme.getBackgroundType() == BackgroundType.COLOR) ?
                    memoTheme.getBackgroundColor() : getBackgroundImageUrl(memoTheme.getBackgroundImageInfo());
            memoThemeSelectResponseDtos.add(MemoThemeSelectResponseDto.builder()
                    .memoThemeId(memoTheme.getId())
                    .memoBackgroundType(memoTheme.getBackgroundType())
                    .memoBackground(memoBackgroundValue)
                    .memoTextColor(memoTheme.getTextColor())
                    .build());
        }
        return memoThemeSelectResponseDtos;
    }

    private List<String> getTagValues(Long boardId){
        List<String> tagValues = new ArrayList<String>();
        List<Tag> tags = tagRepository.findAllByBoardId(boardId);
        for(Tag tag:tags){
            tagValues.add(tag.getValue());
        }
        return tagValues;
    }

    private int getMemoCnt(Long boardId){
        return memoRepository.findByBoardId(boardId).size();
    }


    private String getBackgroundImageUrl(ImageInfo imageInfo) {
        return String.format("/image/%s", imageInfo.getUuid());
    }
}
