package com.example.postme.exception;

public class WrongConditionException extends RuntimeException {
    public WrongConditionException(String message) {
        super(message);
    }
}
