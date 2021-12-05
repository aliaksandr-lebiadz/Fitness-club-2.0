package com.epam.fitness.exception.controller;

import com.epam.fitness.controller.ControllerUtils;
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
        System.out.println(ex.getMessage());
        return ControllerUtils.createRedirect(ERROR_PAGE_URL);
    }

}
