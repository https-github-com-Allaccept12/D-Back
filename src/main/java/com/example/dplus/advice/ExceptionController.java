package com.example.dplus.advice;


import com.example.dplus.dto.Fail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {ErrorCustomException.class,})
    public ResponseEntity<Fail> apiBadRequestHandle(ErrorCustomException ex) {
        Fail apiException = new Fail(ex.getErrorCode());
        log.error("에러발생 :" + ex.getErrorCode());
        return new ResponseEntity<>(apiException, HttpStatus.OK);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Fail> handleApiRequestErrorException(MethodArgumentNotValidException ex) {
        String msg = Objects.requireNonNull(ex.getMessage());
        Fail restApiException = new Fail(msg  + " -----test-----" + ex.getLocalizedMessage() );
        log.error(msg + "---- test ----" +ex.getLocalizedMessage());
        return new ResponseEntity<>(restApiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Fail> defaultException(Exception ex) {
        Fail apiException = new Fail(ex.getMessage());
        log.error(apiException.getMsg());
        return new  ResponseEntity<>(apiException, HttpStatus.OK);
    }
}
