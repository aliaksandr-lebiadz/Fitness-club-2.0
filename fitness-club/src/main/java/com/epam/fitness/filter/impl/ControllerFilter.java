package com.epam.fitness.filter.impl;

import com.epam.fitness.filter.helper.CommandAccessController;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.filter.AbstractFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "controllerFilter", urlPatterns = {"/controller"})
public class ControllerFilter extends AbstractFilter {

    private static final String COMMAND_PARAMETER = "command";

    private CommandAccessController commandAccessController;

    @Override
    public void init(FilterConfig filterConfig){
        commandAccessController = new CommandAccessController();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = getUser(request);
        String command = request.getParameter(COMMAND_PARAMETER);
        if(!commandAccessController.hasAccess(command, user)){
            throw new ServletException("Unauthorized access!");
        }
        chain.doFilter(servletRequest, servletResponse);
    }
}