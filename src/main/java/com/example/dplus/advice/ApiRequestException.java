package com.example.dplus.advice;

public class ApiRequestException extends RuntimeException{

    public ApiRequestException(ErrorCode code) {
        super(code.getMessage());
    }
}
