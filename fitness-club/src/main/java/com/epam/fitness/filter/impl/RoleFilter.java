package com.epam.fitness.filter.impl;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.filter.AbstractFilter;
import com.epam.fitness.filter.helper.UserAccessUtils;
import com.epam.fitness.utils.CurrentPageGetter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebFilter(filterName = "roleFilter", urlPatterns = {"/clients", "/trainerClients", "/assignments", "/getMembership", "/orders"})
public class RoleFilter extends AbstractFilter {

    private static final List<String> ADMIN_PAGES = Collections.singletonList("/clients");
    private static final List<String> TRAINER_PAGES = Collections.singletonList("/trainerClients");
    private static final List<String> CLIENT_AND_TRAINER_PAGES = Collections.singletonList("/assignments");
    private static final List<String> CLIENT_PAGES = Arrays.asList("/getMembership", "/orders");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String currentPage = CurrentPageGetter.getCurrentPage(request);
        User user = getUser(request);
        if(!hasAccess(user, currentPage)){
            throw new ServletException("Unauthorized access!");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean hasAccess(User user, String currentPage){
        boolean hasAccess = false;
        if(ADMIN_PAGES.contains(currentPage)) {
            hasAccess = UserAccessUtils.isAdmin(user);
        } else if(TRAINER_PAGES.contains(currentPage)) {
            hasAccess = UserAccessUtils.isTrainer(user);
        } else if(CLIENT_PAGES.contains(currentPage)) {
            hasAccess = UserAccessUtils.isClient(user);
        } else if(CLIENT_AND_TRAINER_PAGES.contains(currentPage)) {
            hasAccess = UserAccessUtils.isClient(user) || UserAccessUtils.isTrainer(user);
        }
        return hasAccess;
    }
}
