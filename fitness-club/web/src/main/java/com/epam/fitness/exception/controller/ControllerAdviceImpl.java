package com.epam.fitness.exception.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdviceImpl extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(ControllerAdviceImpl.class);
    private static final String INVALID_METHOD_ARGUMENTS_MESSAGE = "Invalid method parameter!";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError(INVALID_METHOD_ARGUMENTS_MESSAGE);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAnyException(Exception exception){
        LOGGER.error(exception);

        String message = exception.getMessage();
        ApiError apiError = new ApiError(message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }

}
