package com.epam.fitness.exception;

public class UserNotFoundException extends Exception{

    public UserNotFoundException() {}

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Exception exception) {
        super(message, exception);
    }

    public UserNotFoundException(Exception exception) {
        super(exception);
    }

}
