package com.example.dplus.advice;

public class ApiRequestException extends RuntimeException{

    private ErrorCode errorCode;

    public ApiRequestException(String messaeg, ErrorCode code){
        super(messaeg);
        this.errorCode = code;
    }

    public ApiRequestException(ErrorCode code) {
        super(code.getMessage());
        this.errorCode = code;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }


}
