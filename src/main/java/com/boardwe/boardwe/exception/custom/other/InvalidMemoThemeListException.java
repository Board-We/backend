package com.boardwe.boardwe.exception.custom.other;

import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.CustomException;

public class InvalidMemoThemeListException extends CustomException {
    public InvalidMemoThemeListException() {
        super(ErrorCode.INVALID_MEMO_THEME_LIST);
    }
}
