package com.boardwe.boardwe.exception.custom;

import com.boardwe.boardwe.exception.ErrorCode;

public class BoardBeforeOpenException extends EntityNotFoundException{
    public BoardBeforeOpenException() {
        super(ErrorCode.BOARD_BEFORE_OPEN);
    }
}
