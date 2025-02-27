package com.example.sunhan.domain.exception;

import com.example.sunhan.global.error.ErrorCode;
import com.example.sunhan.global.error.ExceptionBase;

public class NotFoundException extends ExceptionBase {
    public NotFoundException(String message) {
        super(message, ErrorCode.NOT_FOUND);
    }
}
