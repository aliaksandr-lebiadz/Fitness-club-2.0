package com.epam.fitness.filter;

import com.epam.fitness.entity.user.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractFilter implements Filter {

    private static final String USER_ATTRIBUTE = "user";

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}

    protected User getUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (User) session.getAttribute(USER_ATTRIBUTE);
    }
}