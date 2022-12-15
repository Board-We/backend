package com.boardwe.boardwe.exception.custom.other;

import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.CustomException;

public class InvalidPasswordException extends CustomException {
    public InvalidPasswordException() {
        super(ErrorCode.INVALID_PASSWORD);
    }
}
