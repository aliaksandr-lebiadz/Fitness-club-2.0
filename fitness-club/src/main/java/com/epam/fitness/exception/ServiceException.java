package com.epam.fitness.exception;

public class ServiceException extends Exception {

    public ServiceException() {}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Exception exception) {
        super(message, exception);
    }

    public ServiceException(Exception exception) {
        super(exception);
    }

}