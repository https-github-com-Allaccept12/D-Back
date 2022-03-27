package com.example.dplus.advice;

public class ErrorCustomException extends RuntimeException{

    private ErrorCode errorCode;

    public ErrorCustomException(String message, ErrorCode code){
        super(message);
        this.errorCode = code;
    }

    public ErrorCustomException(ErrorCode code) {
        super(code.getMessage());
        this.errorCode = code;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }


}
