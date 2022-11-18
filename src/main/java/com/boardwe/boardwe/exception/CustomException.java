package com.boardwe.boardwe.exception;

public class CustomException extends RuntimeException{
    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(String message,ErrorCode errorCode){
        super(errorCode.getMessage()+message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
