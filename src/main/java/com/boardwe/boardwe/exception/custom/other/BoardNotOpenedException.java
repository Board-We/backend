package com.boardwe.boardwe.exception.custom.other;

import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.CustomException;

public class BoardNotOpenedException extends CustomException {
    public BoardNotOpenedException() {
        super(ErrorCode.BOARD_NOT_OPENED);
    }
}
