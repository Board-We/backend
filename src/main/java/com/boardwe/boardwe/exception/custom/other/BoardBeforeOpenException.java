package com.boardwe.boardwe.exception.custom.other;

import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.CustomException;

public class BoardBeforeOpenException extends CustomException {
    public BoardBeforeOpenException() {
        super(ErrorCode.BOARD_BEFORE_OPEN);
    }
}
