package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.exceptions.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//Application controller advice
@ControllerAdvice
public class ApplicationControllerAdvice {

    //Method for handling CustomerNotFoundException
    @ResponseBody
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String CustomerNotFoundHandler(CustomerNotFoundException ex) {
        return ex.getMessage();
    }
}
