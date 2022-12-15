package com.boardwe.boardwe.exception.custom.other;

import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.CustomException;

public class BoardWritingException extends CustomException {
    public BoardWritingException() {
        super(ErrorCode.BOARD_WRITING);
    }
}
