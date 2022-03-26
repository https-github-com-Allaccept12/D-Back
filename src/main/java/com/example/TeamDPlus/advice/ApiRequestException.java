package com.example.TeamDPlus.advice;

public class ApiRequestException extends RuntimeException{

    public ApiRequestException(ErrorCode code) {
        super(code.getMessage());
    }
}
