package com.boardwe.boardwe.exception.custom.other;

import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.CustomException;

public class BoardClosedException extends CustomException {
    public BoardClosedException() {
        super(ErrorCode.BOARD_CLOSED);
    }
}
