package com.boardwe.boardwe.exception.custom;

import com.boardwe.boardwe.exception.ErrorCode;

public class BoardThemeNotFoundException extends EntityNotFoundException{
    public BoardThemeNotFoundException() {
        super(ErrorCode.BOARD_THEME_NOT_FOUND);
    }
}
