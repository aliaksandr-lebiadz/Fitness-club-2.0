package com.epam.fitness.filter.impl;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.filter.AbstractFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "authorizationFilter",
        urlPatterns = {"/home", "/getMembership", "/orders", "/assignments", "/trainerClients", "/clients"})
public class AuthorizationFilter extends AbstractFilter {

    private static final String LOGIN_PAGE_URL = "/login";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = getUser(request);
        if(user == null){
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + LOGIN_PAGE_URL);
        }
        chain.doFilter(servletRequest, servletResponse);
    }
}