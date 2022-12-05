package com.boardwe.boardwe.exception.custom;

import com.boardwe.boardwe.exception.ErrorCode;

public class BoardNotFoundException extends EntityNotFoundException{
    public BoardNotFoundException() {
        super(ErrorCode.BOARD_NOT_FOUND);
    }
}
