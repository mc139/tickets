package com.tickets.tickets.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PeselNotFoundException extends RuntimeException {
    public PeselNotFoundException() {
    }

    public PeselNotFoundException(String pesel) {
        log.error("Could not find a person with PESEL :" + pesel);
    }
}
