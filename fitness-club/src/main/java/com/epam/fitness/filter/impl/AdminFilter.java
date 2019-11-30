package com.epam.fitness.filter.impl;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.filter.AbstractFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "adminFilter", urlPatterns = {"/clients"})
public class AdminFilter extends AbstractFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = getUser(request);
        if(user != null && user.getRole() != UserRole.ADMIN){
            throw new ServletException("Unauthorized access!");
        }
        chain.doFilter(servletRequest, servletResponse);
    }
}