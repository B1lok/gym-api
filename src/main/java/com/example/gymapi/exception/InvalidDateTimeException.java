package com.example.gymapi.exception;

public class InvalidDateTimeException extends RuntimeException{
    public InvalidDateTimeException(String message) {
        super(message);
    }
}
