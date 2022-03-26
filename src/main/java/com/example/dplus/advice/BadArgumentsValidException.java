package com.example.dplus.advice;

public class BadArgumentsValidException extends RuntimeException{

    public BadArgumentsValidException() {
    }

    public BadArgumentsValidException(ErrorCode code) {
        super(code.getMessage());
    }
}
