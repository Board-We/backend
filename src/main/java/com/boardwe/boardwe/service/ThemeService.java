package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.res.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.res.MemoThemeSelectResponseDto;

import java.util.List;

public interface ThemeService {
    List<BoardThemeSelectResponseDto> getPublicThemes();

    List<MemoThemeSelectResponseDto> getMemoThemesOfBoard(String boardCode);
}
