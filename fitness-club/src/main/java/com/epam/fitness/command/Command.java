package com.epam.fitness.command;

import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    CommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException, ValidationException;

}