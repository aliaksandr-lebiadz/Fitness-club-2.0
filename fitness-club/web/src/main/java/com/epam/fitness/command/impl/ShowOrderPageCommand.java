package com.epam.fitness.command.impl;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.GymMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.epam.fitness.command.Commands.SHOW_ORDER_PAGE_COMMAND;

@Component(SHOW_ORDER_PAGE_COMMAND)
public class ShowOrderPageCommand implements Command {

    private static final String MEMBERSHIPS_ATTRIBUTE = "memberships";
    private static final String GET_MEMBERSHIP_PAGE_URL = "/getMembership";

    private GymMembershipService service;

    @Autowired
    public ShowOrderPageCommand(GymMembershipService service){
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        List<GymMembership> memberships = service.getAll();
        request.setAttribute(MEMBERSHIPS_ATTRIBUTE, memberships);
        return CommandResult.forward(GET_MEMBERSHIP_PAGE_URL);
    }
}
