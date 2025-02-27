package com.example.sunhan.global.error;

import lombok.Getter;

@Getter
public class ExceptionBase extends RuntimeException{
    private final ErrorCode errorCode;

    public ExceptionBase(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
