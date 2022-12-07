package com.boardwe.boardwe.util.impl;

import com.boardwe.boardwe.dto.res.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.res.MemoThemeSelectResponseDto;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.entity.ThemeCategory;
import com.boardwe.boardwe.repository.MemoThemeRepository;
import com.boardwe.boardwe.repository.TagRepository;
import com.boardwe.boardwe.repository.ThemeCategoryRepository;
import com.boardwe.boardwe.type.BackgroundType;
import com.boardwe.boardwe.util.ThemeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ThemeUtilImpl implements ThemeUtil {

    private final ThemeCategoryRepository categoryRepository;
    private final MemoThemeRepository memoThemeRepository;

    private final String USER_THEME_NAME = "TEMP";

    @Override
    public ThemeCategory getUserThemeCategory() {
        List<ThemeCategory> tempCategories = categoryRepository.findByName(USER_THEME_NAME);
        if (tempCategories.isEmpty()) {
            return categoryRepository.save(
                    ThemeCategory.builder()
                            .name(USER_THEME_NAME)
                            .build());
        } else {
            return tempCategories.get(0);
        }
    }

    @Override
    public String getUserThemeName() {
        return USER_THEME_NAME;
    }

    @Override
    public BoardThemeSelectResponseDto getBoardThemeSelectResponseDto(BoardTheme boardTheme) {
        return BoardThemeSelectResponseDto.builder()
                .boardBackground(getBackgroundValue(boardTheme))
                .boardBackgroundType(boardTheme.getBackgroundType())
                .boardFont(boardTheme.getFont())
                .memoThemes(getMemoThemeSelectResponseDtos(boardTheme))
                .build();
    }

    @Override
    public List<MemoThemeSelectResponseDto> getMemoThemeSelectResponseDtos(BoardTheme boardTheme) {
        List<MemoTheme> memoThemes = memoThemeRepository.findByBoardThemeId(boardTheme.getId());
        List<MemoThemeSelectResponseDto> memoThemeResponseDtos = new ArrayList<>();
        for (MemoTheme memoTheme : memoThemes) {
            MemoThemeSelectResponseDto memoThemeResponseDto = MemoThemeSelectResponseDto.builder()
                    .memoThemeId(memoTheme.getId())
                    .memoBackgroundType(memoTheme.getBackgroundType())
                    .memoBackground(getBackgroundValue(memoTheme))
                    .memoTextColor(memoTheme.getTextColor())
                    .build();
            memoThemeResponseDtos.add(memoThemeResponseDto);
        }
        return memoThemeResponseDtos;
    }


    @Override
    public String getBackgroundValue(BoardTheme boardTheme) {
        return boardTheme.getBackgroundType() == BackgroundType.COLOR?
                boardTheme.getBackgroundColor()
                : String.format("/image/%s", boardTheme.getBackgroundImageInfo().getUuid());
    }

    @Override
    public String getBackgroundValue(MemoTheme memoTheme) {
        return memoTheme.getBackgroundType() == BackgroundType.COLOR?
                memoTheme.getBackgroundColor()
                : String.format("/image/%s", memoTheme.getBackgroundImageInfo().getUuid());
    }
}
