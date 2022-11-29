package com.boardwe.boardwe.exception.custom;

import com.boardwe.boardwe.exception.ErrorCode;

public class BoardClosedException extends EntityNotFoundException{
    public BoardClosedException() {
        super(ErrorCode.BOARD_CLOSED);
    }
}
