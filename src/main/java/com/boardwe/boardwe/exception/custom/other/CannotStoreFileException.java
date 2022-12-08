package com.boardwe.boardwe.exception.custom.other;

import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.CustomException;

public class CannotStoreFileException extends CustomException {
    public CannotStoreFileException() {
        super(ErrorCode.CANNOT_STORE_FILE);
    }
}
