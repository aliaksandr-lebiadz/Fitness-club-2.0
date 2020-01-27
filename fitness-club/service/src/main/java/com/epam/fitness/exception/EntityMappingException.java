package com.epam.fitness.exception;

public class EntityMappingException extends Exception {

    public EntityMappingException() {}

    public EntityMappingException(String message) {
        super(message);
    }

    public EntityMappingException(String message, Exception exception) {
        super(message, exception);
    }

    public EntityMappingException(Exception exception) {
        super(exception);
    }

}
