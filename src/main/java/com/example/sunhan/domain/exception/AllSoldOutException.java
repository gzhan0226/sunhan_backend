package com.example.sunhan.domain.exception;

public class AllSoldOutException extends RuntimeException{
    public AllSoldOutException(String message) {
        super(message);
    }
}
