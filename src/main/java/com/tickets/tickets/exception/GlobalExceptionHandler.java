package com.tickets.tickets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List<String> handleBindException(BindException exception) {
        List<String> errors = new ArrayList<>();
        exception.getFieldErrors().forEach(err -> errors.add(err.getDefaultMessage()));
        return errors;
    }

    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String BoatNotFoundHandler(PersonNotFoundException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(PeselNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String BoatNotFoundHandler(PeselNotFoundException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String BoatNotFoundHandler(TicketNotFoundException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(TrafficOffenceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String BoatNotFoundHandler(TrafficOffenceNotFoundException exception) {
        return exception.getMessage();
    }

}
