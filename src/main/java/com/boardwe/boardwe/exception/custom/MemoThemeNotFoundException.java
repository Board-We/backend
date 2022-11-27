package com.boardwe.boardwe.exception.custom;

import com.boardwe.boardwe.exception.ErrorCode;

public class MemoThemeNotFoundException extends EntityNotFoundException{
    public MemoThemeNotFoundException() {
        super(ErrorCode.MEMO_THEME_NOT_FOUND);
    }
}
