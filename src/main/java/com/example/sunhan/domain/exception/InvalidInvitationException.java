package com.example.sunhan.domain.exception;

import com.example.sunhan.global.error.ErrorCode;
import com.example.sunhan.global.error.ExceptionBase;

public class InvalidInvitationException extends ExceptionBase {
    public InvalidInvitationException(String message) {
        super(message, ErrorCode.INVALID_INVITATION);
    }
}
