package com.boardwe.boardwe.exception.custom;

import com.boardwe.boardwe.exception.ErrorCode;

public class MemoNotFoundException extends EntityNotFoundException{
    public MemoNotFoundException() {
        super(ErrorCode.MEMO_NOT_FOUND);
    }
}
