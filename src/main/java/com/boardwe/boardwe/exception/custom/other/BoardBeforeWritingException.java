package com.boardwe.boardwe.exception.custom.other;

import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.CustomException;

public class BoardBeforeWritingException extends CustomException {
    public BoardBeforeWritingException() {
        super(ErrorCode.BOARD_BEFORE_WRITING);
    }
}
