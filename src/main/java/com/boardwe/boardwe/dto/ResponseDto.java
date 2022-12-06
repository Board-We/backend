package com.boardwe.boardwe.dto;

import com.boardwe.boardwe.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseDto {

    private int status;

    private String message;

    private LocalDateTime timestamp = LocalDateTime.now();

    private Map<String, Object> data = new HashMap<>();

    private Map<String, Object> errors = new HashMap<>();

    public ResponseDto(HttpStatus httpStatus) {
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
    }

    public void addData(String name, Object value){
        this.data.put(name, value);
    }

    public void addError(String name, Object value){
        this.errors.put(name, value);
    }

    public static ResponseDto ok() {
        return new ResponseDto(HttpStatus.OK);
    }

    public static ResponseDto ok(String name, Object data) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setMessage(HttpStatus.OK.getReasonPhrase());
        responseDto.addData(name, data);
        return responseDto;
    }

    public static ResponseDto created(Long id) {
        return ResponseDto.ok("id", id);
    }

    public static ResponseDto error(ErrorCode code){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(code.getStatus());
        responseDto.setMessage(code.getMessage());
        return responseDto;
    }

    public static ResponseDto error(ErrorCode code, String message){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(code.getStatus());
        responseDto.setMessage(message);
        return responseDto;
    }

}

