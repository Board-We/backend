package com.boardwe.boardwe.exception.custom.other;

import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.CustomException;

public class BoardCannotWriteException extends CustomException {
    public BoardCannotWriteException() {
        super(ErrorCode.BOARD_CANNOT_WRITE);
    }
}
