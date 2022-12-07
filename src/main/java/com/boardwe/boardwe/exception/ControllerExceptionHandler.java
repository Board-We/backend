package com.boardwe.boardwe.exception;

import com.boardwe.boardwe.exception.custom.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseDto> handleCustomException(final CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponseDto response = ErrorResponseDto.error(errorCode);
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponseDto> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        ErrorResponseDto response = ErrorResponseDto.error(errorCode);
        return ResponseEntity.status(errorCode.getStatus())
                .body(response);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponseDto> handleBindException(BindException e) {
        ErrorCode errorCode = ErrorCode.REQUEST_ERROR;
        ErrorResponseDto response = ErrorResponseDto.error(errorCode);
        return ResponseEntity.status(errorCode.getStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseDto> handleException() {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        ErrorResponseDto response = ErrorResponseDto.error(errorCode);
        return ResponseEntity.status(errorCode.getStatus())
                .body(response);
    }

}
