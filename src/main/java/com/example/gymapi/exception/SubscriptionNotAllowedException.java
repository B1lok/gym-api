package com.example.gymapi.exception;

public class SubscriptionNotAllowedException extends RuntimeException{
    public SubscriptionNotAllowedException(String message) {
        super(message);
    }
}
