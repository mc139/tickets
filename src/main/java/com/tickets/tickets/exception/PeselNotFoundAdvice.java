package com.tickets.tickets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PeselNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PeselNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String BoatNotFoundHandler(PeselNotFoundException exception) {
        return exception.getMessage();
    }
}
