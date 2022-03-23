package TeamDPlus.code.advice;


import TeamDPlus.code.dto.Fail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ExceptionController {



    @ExceptionHandler(value = {BadArgumentsValidException.class})
    public ResponseEntity<Fail> badRequestHandle(IllegalArgumentException ex) {
        Fail notFoundException = new Fail(ex.getMessage());
        return new ResponseEntity<>(notFoundException, HttpStatus.OK);
    }

    @ExceptionHandler(value = {ApiRequestException.class,})
    public ResponseEntity<Fail> apiBadRequestHandle(ApiRequestException ex) {
        Fail apiException = new Fail(ex.getMessage());
        return new ResponseEntity<>(apiException, HttpStatus.OK);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Fail> handleApiRequestErrorException(MethodArgumentNotValidException ex) {
        String msg = Objects.requireNonNull(ex.getMessage());
        Fail restApiException = new Fail(msg);
        return new ResponseEntity<>(restApiException, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Fail> defaultException(Exception ex) {
        Fail apiException = new Fail("알 수 없는 오류 입니다. 관리자에게 문의 바랍니다.");
        return new  ResponseEntity<>(apiException, HttpStatus.OK);
    }
}
