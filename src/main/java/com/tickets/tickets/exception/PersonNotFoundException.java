package com.tickets.tickets.exception;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException() {
    }

    public PersonNotFoundException(String message) {
        super(message);
    }
}
