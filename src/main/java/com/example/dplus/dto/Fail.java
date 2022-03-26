package com.example.dplus.dto;

import com.example.dplus.advice.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Fail {

    private String result = "fail";
    private String msg;
    private int status;
    private String code;

    public Fail(final ErrorCode errorCode){
        this.msg = errorCode.getMessage();
        this.status = errorCode.getStatusCode();
        this.code = errorCode.getCode();
    }

    public Fail(final String msg, int status){
        this.msg = msg;
        this.status = status;
    }

    public Fail(final String msg){
        this.msg = msg;
    }
}
