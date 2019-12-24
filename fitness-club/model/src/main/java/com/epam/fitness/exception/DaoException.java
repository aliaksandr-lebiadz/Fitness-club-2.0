package com.epam.fitness.exception;

public class DaoException extends Exception {

    public DaoException() {}

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Exception exception) {
        super(message, exception);
    }

    public DaoException(Exception exception) {
        super(exception);
    }
}