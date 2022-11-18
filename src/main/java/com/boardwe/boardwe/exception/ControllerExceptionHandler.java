package com.boardwe.boardwe.exception;

import com.boardwe.boardwe.dto.ResponseDto;
import com.boardwe.boardwe.exception.custom.CustomException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseDto handleCustomException(final CustomException e) {
        return ResponseDto.error(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseDto handleException() {
        return ResponseDto.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }

}
