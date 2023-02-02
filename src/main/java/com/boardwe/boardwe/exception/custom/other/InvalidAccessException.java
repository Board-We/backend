package com.boardwe.boardwe.exception.custom.other;

import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.CustomException;

public class InvalidAccessException extends CustomException {
    public InvalidAccessException() {super(ErrorCode.INVALID_ACCESS);}
}
