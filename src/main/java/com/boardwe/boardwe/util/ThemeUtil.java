package com.boardwe.boardwe.util;

import com.boardwe.boardwe.dto.res.BoardThemeSelectResponseDto;
import com.boardwe.boardwe.dto.res.MemoThemeSelectResponseDto;
import com.boardwe.boardwe.entity.BoardTheme;
import com.boardwe.boardwe.entity.MemoTheme;
import com.boardwe.boardwe.entity.ThemeCategory;

import java.util.List;

public interface ThemeUtil {
    ThemeCategory getUserThemeCategory();
    String getUserThemeName();
    BoardThemeSelectResponseDto getBoardThemeSelectResponseDto(BoardTheme boardTheme);
    List<MemoThemeSelectResponseDto> getMemoThemeSelectResponseDtos(BoardTheme boardTheme);
    String getBackgroundValue(BoardTheme boardTheme);
    String getBackgroundValue(MemoTheme memoTheme);
}
