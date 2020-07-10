package com.epam.fitness.exception;

public class EntityNotFoundException extends ServiceException {

    public EntityNotFoundException() {}

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Exception exception) {
        super(message, exception);
    }

    public EntityNotFoundException(Exception exception) {
        super(exception);
    }

}
