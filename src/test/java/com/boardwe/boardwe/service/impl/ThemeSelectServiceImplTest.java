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
import com.boardwe.boardwe.type.BackgroundType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThemeSelectServiceImplTest {

    @Mock
    private ThemeCategoryRepository categoryRepository;
    @Mock
    private BoardThemeRepository boardThemeRepository;
    @Mock
    private MemoThemeRepository memoThemeRepository;

    @InjectMocks
    private ThemeSelectServiceImpl themeSelectService;

    private final String backgroundColor = "#FFFFFF",
            backgroundImageUuid = "abcd",
            font = "Batang",
            textColor = "#000000";

    @Test
    @DisplayName("사용 가능한 모든 보드 테마를 조회한다.")
    void get_public_board_themes() throws Exception {
        // given
        ThemeCategory templateCategory = mock(ThemeCategory.class);
        ThemeCategory publicCategory = mock(ThemeCategory.class);
        when(templateCategory.getId()).thenReturn(101L);
        when(publicCategory.getId()).thenReturn(102L);
        when(templateCategory.getName()).thenReturn("크리스마스");
        when(publicCategory.getName()).thenReturn("생일");

        BoardTheme boardTheme1 = setBoardTheme(BackgroundType.IMAGE);
        BoardTheme boardTheme2 = setBoardTheme(BackgroundType.COLOR);
        List<MemoTheme> memoThemes = List.of(setMemoTheme(BackgroundType.COLOR),
                setMemoTheme(BackgroundType.IMAGE));

        when(categoryRepository.findByNameNot("TEMP"))
                .thenReturn(List.of(templateCategory, publicCategory));
        when(boardThemeRepository.findByThemeCategoryId(101L))
                .thenReturn(List.of(boardTheme1));
        when(boardThemeRepository.findByThemeCategoryId(102L))
                .thenReturn(List.of(boardTheme2));
        when(memoThemeRepository.findByBoardThemeId(boardTheme1.getId()))
                .thenReturn(memoThemes);

        // when
        List<BoardThemeSelectResponseDto> themes = themeSelectService.getPublicThemes();

        // then
        assertEquals(BackgroundType.IMAGE, themes.get(0).getBoardBackgroundType());
        assertEquals("/image/" + backgroundImageUuid, themes.get(0).getBoardBackground());
        assertEquals(font, themes.get(0).getBoardFont());
        assertMemoThemeResponseDtos(themes.get(0).getMemoThemes());
        assertEquals(BackgroundType.COLOR, themes.get(1).getBoardBackgroundType());
        assertEquals(backgroundColor, themes.get(1).getBoardBackground());
        assertEquals(font, themes.get(1).getBoardFont());
        assertMemoThemeResponseDtos(themes.get(1).getMemoThemes());
    }

    private BoardTheme setBoardTheme(BackgroundType backgroundType) {
        BoardTheme theme = mock(BoardTheme.class);
        when(theme.getId()).thenReturn(100L);
        when(theme.getBackgroundType()).thenReturn(backgroundType);
        when(theme.getFont()).thenReturn(font);

        if (backgroundType == BackgroundType.COLOR) {
            when(theme.getBackgroundColor()).thenReturn(backgroundColor);
        } else {
            ImageInfo imageInfo = mock(ImageInfo.class);
            when(imageInfo.getUuid()).thenReturn(backgroundImageUuid);
            when(theme.getBackgroundImageInfo()).thenReturn(imageInfo);
        }
        return theme;
    }

    private MemoTheme setMemoTheme(BackgroundType backgroundType) {
        MemoTheme theme = mock(MemoTheme.class);
        when(theme.getBackgroundType()).thenReturn(backgroundType);
        when(theme.getTextColor()).thenReturn(textColor);

        if (backgroundType == BackgroundType.COLOR) {
            when(theme.getBackgroundColor()).thenReturn(backgroundColor);
        } else {
            ImageInfo imageInfo = mock(ImageInfo.class);
            when(imageInfo.getUuid()).thenReturn(backgroundImageUuid);
            when(theme.getBackgroundImageInfo()).thenReturn(imageInfo);
        }
        return theme;
    }

    private void assertMemoThemeResponseDtos(List<MemoThemeResponseDto> memoThemes) {
        assertEquals(BackgroundType.COLOR, memoThemes.get(0).getMemoBackgroundType());
        assertEquals(backgroundColor, memoThemes.get(0).getMemoBackground());
        assertEquals(textColor, memoThemes.get(0).getMemoTextColor());
        assertEquals(BackgroundType.IMAGE, memoThemes.get(1).getMemoBackgroundType());
        assertEquals("/image/" + backgroundImageUuid, memoThemes.get(1).getMemoBackground());
        assertEquals(textColor, memoThemes.get(1).getMemoTextColor());
    }
}