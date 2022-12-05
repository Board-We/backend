package com.boardwe.boardwe.exception.custom;

import com.boardwe.boardwe.exception.ErrorCode;

public class MemoWithInvalidBoardException extends CustomException{
    public MemoWithInvalidBoardException() {
        super(ErrorCode.MEMO_WITH_INVALID_BOARD);
    }
}
