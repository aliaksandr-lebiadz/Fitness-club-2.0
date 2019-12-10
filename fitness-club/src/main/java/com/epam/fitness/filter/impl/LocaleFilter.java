package com.epam.fitness.filter.impl;

import com.epam.fitness.filter.AbstractFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "localeFilter", urlPatterns = {"/*"})
public class LocaleFilter extends AbstractFilter {

    private static final Locale DEFAULT_LOCALE = new Locale("en", "US");
    private static final String LOCALE_ATTRIBUTE = "locale";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        if(session.getAttribute(LOCALE_ATTRIBUTE) == null){
            session.setAttribute(LOCALE_ATTRIBUTE, DEFAULT_LOCALE);
        }
        chain.doFilter(request, servletResponse);
    }
}