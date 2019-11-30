package com.epam.fitness.command.impl;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowClientsCommand implements Command {

    private static final String CLIENTS_ATTRIBUTE = "clients";
    private static final String CLIENTS_PAGE_URL = "/clients";

    private UserService service;

    public ShowClientsCommand(UserService service){
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException {
        List<User> clients = service.getAllClients();
        request.setAttribute(CLIENTS_ATTRIBUTE, clients);
        return CommandResult.forward(CLIENTS_PAGE_URL);
    }
}
