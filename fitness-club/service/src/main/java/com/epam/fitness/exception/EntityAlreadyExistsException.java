package com.epam.fitness.exception;

public class EntityAlreadyExistsException extends ServiceException {

    public EntityAlreadyExistsException() {}

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public EntityAlreadyExistsException(String message, Exception exception) {
        super(message, exception);
    }

    public EntityAlreadyExistsException(Exception exception) {
        super(exception);
    }

}
