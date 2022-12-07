package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.dto.res.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.res.MemoThemeSelectResponseDto;
import com.boardwe.boardwe.entity.*;
import com.boardwe.boardwe.exception.custom.entity.BoardNotFoundException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.repository.BoardThemeRepository;
import com.boardwe.boardwe.repository.ThemeCategoryRepository;
import com.boardwe.boardwe.type.BackgroundType;
import com.boardwe.boardwe.util.ThemeUtil;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThemeServiceImplTest {

    @Mock
    private BoardRepository boardRepository;
    @Mock
    private ThemeCategoryRepository categoryRepository;
    @Mock
    private BoardThemeRepository boardThemeRepository;
    @Mock
    private ThemeUtil themeUtil;

    @InjectMocks
    private ThemeServiceImpl themeSelectService;

    private final String backgroundColor = "#FFFFFF",
            backgroundImageUuid = "abcd",
            font = "Batang",
            textColor = "#000000";

    @Test
    @DisplayName("사용 가능한 모든 보드 테마를 조회한다.")
    void get_public_board_themes() throws Exception {
        // given
        // 테마 카테고리 mock 생성 및 stubbing
        ThemeCategory userCategory = mock(ThemeCategory.class);
        ThemeCategory templateCategory = mock(ThemeCategory.class);
        ThemeCategory publicCategory = mock(ThemeCategory.class);
        when(templateCategory.getId()).thenReturn(101L);
        when(publicCategory.getId()).thenReturn(102L);
        when(userCategory.getName()).thenReturn("TEMP");
        when(templateCategory.getName()).thenReturn("크리스마스");
        when(publicCategory.getName()).thenReturn("생일");

        // 보드 테마 엔티티 생성
        BoardTheme boardTheme1 = BoardTheme.builder()
                .backgroundType(BackgroundType.IMAGE)
                .font(font)
                .build();
        BoardTheme boardTheme2 = BoardTheme.builder()
                .backgroundType(BackgroundType.COLOR)
                .font(font)
                .build();

        // 각 mock에 대한 조회 메소드 stubbing
        when(themeUtil.getUserThemeCategory())
                .thenReturn(userCategory);
        when(themeUtil.getBackgroundValue(boardTheme1))
                .thenReturn("/image/" + backgroundImageUuid);
        when(themeUtil.getBackgroundValue(boardTheme2))
                .thenReturn(backgroundColor);
        when(categoryRepository.findByNameNot("TEMP"))
                .thenReturn(List.of(templateCategory, publicCategory));
        when(boardThemeRepository.findByThemeCategoryId(101L))
                .thenReturn(List.of(boardTheme1));
        when(boardThemeRepository.findByThemeCategoryId(102L))
                .thenReturn(List.of(boardTheme2));

        // when
        List<BoardThemeSelectResponseDto> responseDtos = themeSelectService.getPublicThemes();

        // then
        BoardThemeSelectResponseDto responseBoardTheme1 = responseDtos.get(0);
        assertEquals(BackgroundType.IMAGE, responseBoardTheme1.getBoardBackgroundType());
        assertEquals("/image/" + backgroundImageUuid, responseBoardTheme1.getBoardBackground());
        assertEquals(font, responseBoardTheme1.getBoardFont());

        BoardThemeSelectResponseDto responseBoardTheme2 = responseDtos.get(1);
        assertEquals(BackgroundType.COLOR, responseBoardTheme2.getBoardBackgroundType());
        assertEquals(backgroundColor, responseBoardTheme2.getBoardBackground());
        assertEquals(font, responseBoardTheme2.getBoardFont());
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

        // 메모 테마 mock 생성
        List<MemoThemeSelectResponseDto> memoThemes = List.of(setMemoTheme(BackgroundType.COLOR),
                setMemoTheme(BackgroundType.IMAGE));

        // 각 mock에 대한 조회 메소드 stubbing
        when(boardRepository.findByCode(boardCode))
                .thenReturn(Optional.of(board));
        when(themeUtil.getMemoThemeSelectResponseDtos(boardTheme))
                .thenReturn(memoThemes);

        // when
        List<MemoThemeSelectResponseDto> responseDtos = themeSelectService.getMemoThemesOfBoard(boardCode);

        // then
        assertMemoThemeResponseDtos(responseDtos);
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

    private MemoThemeSelectResponseDto setMemoTheme(BackgroundType backgroundType) {
        return MemoThemeSelectResponseDto.builder()
                .memoThemeId(200L)
                .memoBackgroundType(backgroundType)
                .memoTextColor(textColor)
                .memoBackground(backgroundType == BackgroundType.COLOR? backgroundColor : "/image/" + backgroundImageUuid)
                .build();
    }

    private void assertMemoThemeResponseDtos(List<MemoThemeSelectResponseDto> memoThemes) {
        assertEquals(200L, memoThemes.get(0).getMemoThemeId());
        assertEquals(BackgroundType.COLOR, memoThemes.get(0).getMemoBackgroundType());
        assertEquals(backgroundColor, memoThemes.get(0).getMemoBackground());
        assertEquals(textColor, memoThemes.get(0).getMemoTextColor());

        assertEquals(200L, memoThemes.get(1).getMemoThemeId());
        assertEquals(BackgroundType.IMAGE, memoThemes.get(1).getMemoBackgroundType());
        assertEquals("/image/" + backgroundImageUuid, memoThemes.get(1).getMemoBackground());
        assertEquals(textColor, memoThemes.get(1).getMemoTextColor());
    }
}