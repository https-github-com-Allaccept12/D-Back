package TeamDPlus.code.advice;

public class ApiRequestException extends RuntimeException{

    public ApiRequestException(ErrorCode code) {
        super(code.getMessage());
    }
}
