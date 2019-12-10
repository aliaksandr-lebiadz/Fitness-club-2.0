package com.epam.fitness.command.impl;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.GymMembershipService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowOrderPageCommand implements Command {

    private static final String MEMBERSHIPS_ATTRIBUTE = "memberships";
    private static final String GET_MEMBERSHIP_PAGE_URL = "/getMembership";

    private GymMembershipService service;

    public ShowOrderPageCommand(GymMembershipService service){
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException {
        List<GymMembership> memberships = service.getAll();
        request.setAttribute(MEMBERSHIPS_ATTRIBUTE, memberships);
        return CommandResult.forward(GET_MEMBERSHIP_PAGE_URL);
    }
}
