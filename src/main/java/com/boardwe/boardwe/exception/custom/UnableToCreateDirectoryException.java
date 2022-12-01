package com.boardwe.boardwe.exception.custom;

import com.boardwe.boardwe.exception.ErrorCode;

public class UnableToCreateDirectoryException extends EntityNotFoundException{
    public UnableToCreateDirectoryException() {
        super(ErrorCode.UNABLE_TO_CREATE_DIRECTORY);
    }
}
