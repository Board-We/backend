package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.BoardThemeResponseDto;
import com.boardwe.boardwe.dto.MemoThemeResponseDto;

import java.util.List;

public interface ThemeSelectService {
    List<BoardThemeResponseDto> getPublicThemes();

    List<MemoThemeResponseDto> getMemoThemes(String boardCode);
}
