package com.boardwe.boardwe.exception.custom;

import com.boardwe.boardwe.exception.ErrorCode;

public class CannotStoreFileException extends CustomException{
    public CannotStoreFileException() {
        super(ErrorCode.CANNOT_STORE_FILE);
    }
}
