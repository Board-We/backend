package com.boardwe.boardwe.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponseDto {

    private String message;

    private Map<String, Object> errors = new HashMap<>();

    public void addError(String name, Object value) {
        this.errors.put(name, value);
    }

    public static ErrorResponseDto error(ErrorCode code) {
        ErrorResponseDto responseDto = new ErrorResponseDto();
        responseDto.setMessage(code.getMessage());
        return responseDto;
    }

    public static ErrorResponseDto error(ErrorCode code, String message) {
        ErrorResponseDto responseDto = new ErrorResponseDto();
        responseDto.setMessage(message);
        return responseDto;
    }


}
