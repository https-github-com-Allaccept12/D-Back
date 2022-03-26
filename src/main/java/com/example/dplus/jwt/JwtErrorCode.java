package com.example.dplus.jwt;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum JwtErrorCode {
    UNAUTHORIZEDException(401, "로그인 후 이용가능합니다.", HttpStatus.UNAUTHORIZED),
    ExpiredJwtException(444, "기존 토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED);

    @Getter
    private int code;

    @Getter
    private String message;

    @Getter
    private HttpStatus status;

    JwtErrorCode(final int code, final String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
