package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.MemoThemeResponseDto;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.ImageInfo;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.entity.ThemeCategory;
import com.boardwe.boardwe.repository.BoardThemeRepository;
import com.boardwe.boardwe.repository.MemoThemeRepository;
import com.boardwe.boardwe.repository.ThemeCategoryRepository;
import com.boardwe.boardwe.service.ThemeSelectService;
import com.boardwe.boardwe.type.BackgroundType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeSelectServiceImpl implements ThemeSelectService {

    private final ThemeCategoryRepository categoryRepository;
    private final BoardThemeRepository boardThemeRepository;
    private final MemoThemeRepository memoThemeRepository;

    @Override
    public List<BoardThemeSelectResponseDto> getPublicThemes() {
        List<BoardThemeSelectResponseDto> boardThemeSelectResponseDtos = new ArrayList<>();
        List<ThemeCategory> themeCategories = categoryRepository.findByNameNot("TEMP");

        for (ThemeCategory themeCategory : themeCategories) {
            List<BoardTheme> boardThemes = boardThemeRepository.findByThemeCategoryId(themeCategory.getId());
            for (BoardTheme boardTheme : boardThemes) {
                boardThemeSelectResponseDtos.add(getThemeResponseDto(boardTheme, themeCategory.getName()));
            }
        }

        return boardThemeSelectResponseDtos;
    }

    @Override
    public List<MemoThemeResponseDto> getMemoThemes(String boardCode) {
        return null;
    }

    private BoardThemeSelectResponseDto getThemeResponseDto(BoardTheme boardTheme, String category) {
        String boardBackgroundValue = (boardTheme.getBackgroundType() == BackgroundType.COLOR) ?
                boardTheme.getBackgroundColor() : getBackgroundImageUrl(boardTheme.getBackgroundImageInfo());

        return BoardThemeSelectResponseDto.builder()
                .id(boardTheme.getId())
                .name(boardTheme.getName())
                .category(category)
                .boardBackgroundType(boardTheme.getBackgroundType())
                .boardBackground(boardBackgroundValue)
                .boardFont(boardTheme.getFont())
                .memoThemes(getMemoThemeResponseDtos(boardTheme))
                .build();
    }

    private List<MemoThemeResponseDto> getMemoThemeResponseDtos(BoardTheme boardTheme) {
        List<MemoTheme> memoThemes = memoThemeRepository.findByBoardThemeId(boardTheme.getId());
        List<MemoThemeResponseDto> memoThemeResponseDtos = new ArrayList<>();
        for (MemoTheme memoTheme : memoThemes) {
            String memoBackgroundValue = (memoTheme.getBackgroundType() == BackgroundType.COLOR) ?
                    memoTheme.getBackgroundColor() : getBackgroundImageUrl(memoTheme.getBackgroundImageInfo());
            memoThemeResponseDtos.add(MemoThemeResponseDto.builder()
                    .memoBackgroundType(memoTheme.getBackgroundType())
                    .memoBackground(memoBackgroundValue)
                    .memoTextColor(memoTheme.getTextColor())
                    .build());
        }
        return memoThemeResponseDtos;
    }

    private String getBackgroundImageUrl(ImageInfo imageInfo) {
        return String.format("/image/%s", imageInfo.getUuid());
    }
}
