package com.boardwe.boardwe.service;

import com.boardwe.boardwe.dto.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.MemoThemeResponseDto;

import java.util.List;

public interface ThemeSelectService {
    List<BoardThemeSelectResponseDto> getPublicThemes();

    List<MemoThemeResponseDto> getMemoThemes(String boardCode);
}
