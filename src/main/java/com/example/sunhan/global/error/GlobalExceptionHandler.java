package com.example.sunhan.global.error;

import com.example.sunhan.domain.exception.AllSoldOutException;
import com.example.sunhan.domain.exception.InvalidInvitationException;
import com.example.sunhan.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AllSoldOutException.class)
    public ResponseEntity<ErrorResponse> handleAllSoldOutException(AllSoldOutException ex) {
        ErrorResponse response = new ErrorResponse(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse response = new ErrorResponse(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidInvitationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInvitationException(InvalidInvitationException ex) {
        ErrorResponse response = new ErrorResponse(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
