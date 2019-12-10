package com.epam.fitness.exception;

public class ConnectionPoolException extends Exception{

    public ConnectionPoolException() {}

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Exception exception) {
        super(message, exception);
    }

    public ConnectionPoolException(Exception exception) {
        super(exception);
    }

}