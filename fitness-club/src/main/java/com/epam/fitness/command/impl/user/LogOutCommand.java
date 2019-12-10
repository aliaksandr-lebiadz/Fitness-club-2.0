package com.epam.fitness.command.impl.user;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class LogOutCommand implements Command {

    private static final String LOGIN_PAGE = "/login";
    private static final String LOCALE_ATTRIBUTE = "locale";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(LOCALE_ATTRIBUTE);
        session.invalidate();
        setLocaleToSession(request, locale);
        return CommandResult.redirect(LOGIN_PAGE);
    }

    private void setLocaleToSession(HttpServletRequest request, Locale locale){
        HttpSession session = request.getSession();
        session.setAttribute(LOCALE_ATTRIBUTE, locale);
    }
}