package com.tickets.tickets.exception;

public class PersonNotFoundExceprion extends RuntimeException {
    public PersonNotFoundExceprion() {
    }

    public PersonNotFoundExceprion(String message) {
        super(message);
    }
}
