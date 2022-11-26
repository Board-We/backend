package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.MemoThemeSelectResponseDto;
import com.boardwe.boardwe.entity.*;
import com.boardwe.boardwe.exception.custom.BoardNotFoundException;
import com.boardwe.boardwe.repository.BoardRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThemeSelectServiceImplTest {

    @Mock
    private BoardRepository boardRepository;
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
        // 테마 카테고리 mock 생성 및 stubbing
        ThemeCategory templateCategory = mock(ThemeCategory.class);
        ThemeCategory publicCategory = mock(ThemeCategory.class);
        when(templateCategory.getId()).thenReturn(101L);
        when(publicCategory.getId()).thenReturn(102L);
        when(templateCategory.getName()).thenReturn("크리스마스");
        when(publicCategory.getName()).thenReturn("생일");

        // 보드 테마, 메모 테마 mock 생성
        BoardTheme boardTheme1 = setBoardTheme(BackgroundType.IMAGE);
        BoardTheme boardTheme2 = setBoardTheme(BackgroundType.COLOR);
        List<MemoTheme> memoThemes = List.of(setMemoTheme(BackgroundType.COLOR, false),
                setMemoTheme(BackgroundType.IMAGE, false));

        // 각 mock에 대한 조회 메소드 stubbing
        when(categoryRepository.findByNameNot("TEMP"))
                .thenReturn(List.of(templateCategory, publicCategory));
        when(boardThemeRepository.findByThemeCategoryId(101L))
                .thenReturn(List.of(boardTheme1));
        when(boardThemeRepository.findByThemeCategoryId(102L))
                .thenReturn(List.of(boardTheme2));
        when(memoThemeRepository.findByBoardThemeId(boardTheme1.getId()))
                .thenReturn(memoThemes);

        // when
        List<BoardThemeSelectResponseDto> responseDtos = themeSelectService.getPublicThemes();

        // then
        BoardThemeSelectResponseDto responseBoardTheme1 = responseDtos.get(0);
        assertEquals(BackgroundType.IMAGE, responseBoardTheme1.getBoardBackgroundType());
        assertEquals("/image/" + backgroundImageUuid, responseBoardTheme1.getBoardBackground());
        assertEquals(font, responseBoardTheme1.getBoardFont());
        assertMemoThemeResponseDtos(responseBoardTheme1.getMemoThemes(), false);

        BoardThemeSelectResponseDto responseBoardTheme2 = responseDtos.get(1);
        assertEquals(BackgroundType.COLOR, responseBoardTheme2.getBoardBackgroundType());
        assertEquals(backgroundColor, responseBoardTheme2.getBoardBackground());
        assertEquals(font, responseBoardTheme2.getBoardFont());
        assertMemoThemeResponseDtos(responseBoardTheme2.getMemoThemes(), false);
    }

    @Test
    @DisplayName("특정 보드의 메모 테마를 조회한다.")
    void get_memo_theme_of_board() throws Exception {
        // given
        // 보드 및 보드 테마 mock 생성 및 stubbing
        String boardCode = "1234";
        Board board = mock(Board.class);
        BoardTheme boardTheme = mock(BoardTheme.class);
        when(board.getBoardTheme()).thenReturn(boardTheme);
        when(boardTheme.getId()).thenReturn(100L);

        // 메모 테마 mock 생성
        List<MemoTheme> memoThemes = List.of(setMemoTheme(BackgroundType.COLOR, true),
                setMemoTheme(BackgroundType.IMAGE, true));

        // 각 mock에 대한 조회 메소드 stubbing
        when(boardRepository.findByCode(boardCode))
                .thenReturn(Optional.of(board));
        when(memoThemeRepository.findByBoardThemeId(boardTheme.getId()))
                .thenReturn(memoThemes);

        // when
        List<MemoThemeSelectResponseDto> responseDtos = themeSelectService.getMemoThemesOfBoard(boardCode);

        // then
        assertMemoThemeResponseDtos(responseDtos, true);
    }

    @Test
    @DisplayName("보드가 존재하지 않아서 해당 보드의 메모 테마를 조회에 실패한다.")
    void get_memo_theme_with_invalid_board() throws Exception {
        // given
        String boardCode = "1234";
        when(boardRepository.findByCode(boardCode))
                .thenReturn(Optional.empty());

        // when & then
        assertThrows(BoardNotFoundException.class, () -> themeSelectService.getMemoThemesOfBoard(boardCode));
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

    private MemoTheme setMemoTheme(BackgroundType backgroundType, Boolean withId) {
        MemoTheme theme = mock(MemoTheme.class);
        if (withId) when(theme.getId()).thenReturn(200L);
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

    private void assertMemoThemeResponseDtos(List<MemoThemeSelectResponseDto> memoThemes, Boolean withId) {
        if (withId) assertEquals(200L, memoThemes.get(0).getMemoThemeId());
        assertEquals(BackgroundType.COLOR, memoThemes.get(0).getMemoBackgroundType());
        assertEquals(backgroundColor, memoThemes.get(0).getMemoBackground());
        assertEquals(textColor, memoThemes.get(0).getMemoTextColor());

        if (withId) assertEquals(200L, memoThemes.get(1).getMemoThemeId());
        assertEquals(BackgroundType.IMAGE, memoThemes.get(1).getMemoBackgroundType());
        assertEquals("/image/" + backgroundImageUuid, memoThemes.get(1).getMemoBackground());
        assertEquals(textColor, memoThemes.get(1).getMemoTextColor());
    }
}