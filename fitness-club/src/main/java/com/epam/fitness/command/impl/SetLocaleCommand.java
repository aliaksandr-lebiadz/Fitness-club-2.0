package com.epam.fitness.command.impl;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;
import com.epam.fitness.utils.CurrentPageGetter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class SetLocaleCommand implements Command {

    private static final String LOCALE = "locale";
    private static final String COUNTRY = "country";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String localeValue = request.getParameter(LOCALE);
        String country = request.getParameter(COUNTRY);
        Locale locale = new Locale(localeValue, country);
        HttpSession session = request.getSession();
        session.setAttribute(LOCALE, locale);
        String page = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.redirect(page);
    }
}
