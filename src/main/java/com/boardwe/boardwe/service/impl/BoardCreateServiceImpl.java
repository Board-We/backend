package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.BoardCreateRequestDto;
import com.boardwe.boardwe.dto.Inner.BoardCreateThemeRequestDto;
import com.boardwe.boardwe.dto.Inner.MemoBackgroundTextColorSetsRequestDto;
import com.boardwe.boardwe.dto.Inner.MemoImagesTextColorSetsRequestDto;
import com.boardwe.boardwe.entity.*;
import com.boardwe.boardwe.exception.custom.BoardThemeNotFoundException;
import com.boardwe.boardwe.repository.*;
import com.boardwe.boardwe.service.BoardCreateService;
import com.boardwe.boardwe.type.BackgroundType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final String USER_THEME_NAME = "TEMP";

    @Override
    public String createBoard(BoardCreateRequestDto requestDto) {

        Long requestBoardThemeId = requestDto.getBoardThemeId();
        BoardTheme boardTheme;

        //테마가 기존 테마인지, 새로 만든 테마인지
        if (requestBoardThemeId == null) {
            ThemeCategory themeCategory = themeCategoryRepository.save(ThemeCategory.builder().name(USER_THEME_NAME).build());

            //테마 저장
            BoardCreateThemeRequestDto theme = requestDto.getTheme();
            if (theme.getBoardBackgroundImage() != null) {
                //이미지 저장
                ImageInfo imageInfo = createImageInfo(theme.getBoardBackgroundImageName());

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
            for (MemoImagesTextColorSetsRequestDto imageAndTextColor : theme.getMemoImageTextColorSets()) {
                ImageInfo imageInfo = createImageInfo(imageAndTextColor.getMemoBackgroundImageName());

                memoThemeRepository.save(MemoTheme.builder()
                        .boardTheme(boardTheme)
                        .backgroundType(BackgroundType.IMAGE)
                        .backgroundImageInfo(imageInfo)
                        .build());
            }
            for (MemoBackgroundTextColorSetsRequestDto backgroundTextColor : theme.getMemoBackgroundTextColorSets()) {
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



        return String.format("/board/%s/welcome", boardUuid);
    }

    private ImageInfo createImageInfo(String fileName) {
        String imageUuid = UUID.randomUUID().toString();
        String imageOriginalName = fileName.substring(0, fileName.lastIndexOf("."));
        String imageExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        //이미지 저장경로 정해야함
        String imagePath = "/images";

        return imageInfoRepository.save(ImageInfo.builder()
                .uuid(imageUuid)
                .originalName(imageOriginalName)
                .savedName(imageUuid)
                .extension(imageExtension)
                .path(imagePath).build());
    }
}
