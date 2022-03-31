package com.example.dplus.advice;


import com.example.dplus.dto.Fail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {ErrorCustomException.class,})
    public ResponseEntity<Fail> apiBadRequestHandle(ErrorCustomException ex) {
        Fail apiException = new Fail(ex.getErrorCode());
        log.error("에러발생 :" + ex.getErrorCode());
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Fail> handleApiRequestErrorException(MethodArgumentNotValidException ex) {
        Fail restApiException = new Fail(ex + " " + ex.getLocalizedMessage() );
        log.error(ex.getMessage());
        return new ResponseEntity<>(restApiException, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<Fail> handleMissingRequestParameterErrorException(MethodArgumentNotValidException ex) {
        Fail restApiException = new Fail("API 파라미터값을 잘못입력했거나 입력하지 않았습니다.");
        log.error(ex.getMessage());
        return new ResponseEntity<>(restApiException, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Fail> handleNotSupportedRequestErrorException(MethodArgumentNotValidException ex) {
        Fail restApiException = new Fail("Request 메서드 입력을 잘 못했습니다");
        log.error(ex.getMessage());
        return new ResponseEntity<>(restApiException, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Fail> defaultException(Exception ex) {
//        Fail apiException = new Fail(ex.getMessage());
//        log.error(apiException.getMsg());
//        return new  ResponseEntity<>(apiException, HttpStatus.OK);
//    }
}
