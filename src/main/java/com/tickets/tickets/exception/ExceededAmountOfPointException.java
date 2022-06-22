package com.tickets.tickets.exception;

public class ExceededAmountOfPointException extends RuntimeException {

    public ExceededAmountOfPointException() {
    }

    public ExceededAmountOfPointException(String message) {
        super(message);
    }
}
