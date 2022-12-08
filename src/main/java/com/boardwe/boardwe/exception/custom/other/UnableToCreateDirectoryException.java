package com.boardwe.boardwe.exception.custom.other;

import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.CustomException;

public class UnableToCreateDirectoryException extends CustomException {
    public UnableToCreateDirectoryException() {
        super(ErrorCode.UNABLE_TO_CREATE_DIRECTORY);
    }
}
