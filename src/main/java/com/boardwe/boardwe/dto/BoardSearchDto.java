package com.boardwe.boardwe.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.boardwe.boardwe.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BoardSearchDto {
    private int status;

    private String message;

    private LocalDateTime timestamp = LocalDateTime.now();

    private Object data;

    private Map<String, Object> errors = new HashMap<String, Object>();

    public BoardSearchDto(HttpStatus httpStatus) {
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
    }

    public void addData(Object data){
        this.data = data;
    }

    public void addError(String name, Object value){
        this.errors.put(name, value);
    }

    public static BoardSearchDto ok() {
        return new BoardSearchDto();
    }

    public static BoardSearchDto ok(Object data) {
        BoardSearchDto boardSearchDto = new BoardSearchDto();
        boardSearchDto.setStatus(HttpStatus.OK.value());
        boardSearchDto.setMessage(HttpStatus.OK.getReasonPhrase());
        boardSearchDto.addData(data);
        return boardSearchDto;
    }

    public static BoardSearchDto error(ErrorCode code){
        BoardSearchDto boardSearchDto = new BoardSearchDto();
        boardSearchDto.setStatus(code.getStatus());
        boardSearchDto.setMessage(code.getMessage());
        return boardSearchDto;
    }

    public static BoardSearchDto error(ErrorCode code, String message){
        BoardSearchDto boardSearchDto = new BoardSearchDto();
        boardSearchDto.setStatus(code.getStatus());
        boardSearchDto.setMessage(message);
        return boardSearchDto;
    }
}
