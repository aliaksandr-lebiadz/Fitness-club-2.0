package com.epam.fitness.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ControllerAdviceImpl {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex){
        return ControllerUtils.createRedirect("/error");
    }

}
