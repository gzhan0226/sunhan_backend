package com.example.sunhan.domain.exception;

import com.example.sunhan.global.error.ErrorCode;
import com.example.sunhan.global.error.ExceptionBase;

public class UnauthorizedException extends ExceptionBase {
    public UnauthorizedException (String message) { super(message, ErrorCode.UNAUTHORIZED);
  }
}
