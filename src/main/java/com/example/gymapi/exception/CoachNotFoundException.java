package com.example.gymapi.exception;

public class CoachNotFoundException extends RuntimeException{
    public CoachNotFoundException(String message) {
        super(message);
    }
}
