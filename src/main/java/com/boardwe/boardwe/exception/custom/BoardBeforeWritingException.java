package com.boardwe.boardwe.exception.custom;

import com.boardwe.boardwe.exception.ErrorCode;

public class BoardBeforeWritingException extends EntityNotFoundException{
    public BoardBeforeWritingException() {
        super(ErrorCode.BOARD_BEFORE_WRITING);
    }
}
