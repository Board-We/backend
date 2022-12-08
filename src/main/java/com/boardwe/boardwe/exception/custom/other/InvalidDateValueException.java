package com.boardwe.boardwe.exception.custom.other;

import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.CustomException;

public class InvalidDateValueException extends CustomException {
    public InvalidDateValueException() {
        super(ErrorCode.INVALID_DATE_VALUE);
    }
}
