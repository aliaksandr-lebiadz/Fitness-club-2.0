package com.epam.fitness.exception;

public class ValidationException extends Exception{

    public ValidationException() {}

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Exception exception) {
        super(message, exception);
    }

    public ValidationException(Exception exception) {
        super(exception);
    }

}