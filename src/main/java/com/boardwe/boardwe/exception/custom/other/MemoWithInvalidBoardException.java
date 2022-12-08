package com.boardwe.boardwe.exception.custom.other;

import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.CustomException;

public class MemoWithInvalidBoardException extends CustomException {
    public MemoWithInvalidBoardException() {
        super(ErrorCode.MEMO_WITH_INVALID_BOARD);
    }
}
