package com.tickets.tickets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TrafficOffenceNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(TrafficOffenceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String BoatNotFoundHandler(TrafficOffenceNotFoundException exception) {
        return exception.getMessage();
    }

}
