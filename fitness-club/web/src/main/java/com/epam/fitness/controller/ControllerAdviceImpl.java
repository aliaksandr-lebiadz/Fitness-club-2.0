package com.epam.fitness.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ControllerAdviceImpl {

    private static final Logger LOGGER = LogManager.getLogger(ControllerAdviceImpl.class);
    private static final String ERROR_PAGE_URL = "/error";

    @ExceptionHandler(Exception.class)
    public String handleAnyException(Exception ex){
        LOGGER.error(ex);
        return ControllerUtils.createRedirect(ERROR_PAGE_URL);
    }

}
