package com.epam.fitness.command.impl;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.fitness.command.Commands.SHOW_HOME_PAGE_COMMAND;

@Component(SHOW_HOME_PAGE_COMMAND)
public class ShowHomePageCommand implements Command {

    private static final String HOME_PAGE_URL = "/home";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return CommandResult.redirect(HOME_PAGE_URL);
    }
}