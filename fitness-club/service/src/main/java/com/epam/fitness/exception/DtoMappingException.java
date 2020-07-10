package com.epam.fitness.exception;

public class DtoMappingException extends ServiceException {

    public DtoMappingException() {}

    public DtoMappingException(String message) {
        super(message);
    }

    public DtoMappingException(String message, Exception exception) {
        super(message, exception);
    }

    public DtoMappingException(Exception exception) {
        super(exception);
    }

}
