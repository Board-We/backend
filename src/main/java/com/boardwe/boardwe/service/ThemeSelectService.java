package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.MemoThemeSelectResponseDto;

import java.util.List;

public interface ThemeSelectService {
    List<BoardThemeSelectResponseDto> getPublicThemes();

    List<MemoThemeSelectResponseDto> getMemoThemesOfBoard(String boardCode);
}
