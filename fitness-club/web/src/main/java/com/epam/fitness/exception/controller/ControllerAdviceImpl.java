package com.epam.fitness.exception.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdviceImpl extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(ControllerAdviceImpl.class);
    private static final String ERROR_MESSAGE_FORMAT = "%s %s, but was: %s";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        LOGGER.error(exception);

        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errorsMessages = getMessagesFromFieldsErrors(fieldErrors);
        ApiError apiError = new ApiError(errorsMessages);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAnyException(Exception exception){
        LOGGER.error(exception);

        String message = exception.getMessage();
        ApiError apiError = new ApiError(Collections.singletonList(message));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }

    private List<String> getMessagesFromFieldsErrors(List<FieldError> fieldErrors){
        Function<FieldError, String> formatter =
                (f) -> String.format(ERROR_MESSAGE_FORMAT, f.getField(), f.getDefaultMessage(), f.getRejectedValue());
        return fieldErrors.stream()
                .map(formatter)
                .collect(Collectors.toList());
    }

}
