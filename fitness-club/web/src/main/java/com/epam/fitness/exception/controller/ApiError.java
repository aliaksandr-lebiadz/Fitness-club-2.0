package com.epam.fitness.exception.controller;

import java.util.List;

public class ApiError {

    private List<String> messages;

    /*package-private*/ ApiError(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }

}
