package com.boardwe.boardwe.exception;

import com.boardwe.boardwe.dto.ResponseDto;
import com.boardwe.boardwe.exception.custom.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ResponseDto> handleCustomException(final CustomException e) {
        ResponseDto response = ResponseDto.error(e.getErrorCode());
        return ResponseEntity.status(response.getStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseDto> handleException() {
        ResponseDto response = ResponseDto.error(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(response.getStatus())
                .body(response);
    }

}
