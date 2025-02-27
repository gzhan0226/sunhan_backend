package com.example.sunhan.domain.exception;

import com.example.sunhan.global.error.ErrorCode;
import com.example.sunhan.global.error.ExceptionBase;

public class AllSoldOutException extends ExceptionBase {
    public AllSoldOutException(String message) {
        super(message, ErrorCode.ALL_SOLD_OUT);
    }
}
