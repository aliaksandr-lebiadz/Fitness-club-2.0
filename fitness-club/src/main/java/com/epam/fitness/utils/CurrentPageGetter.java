package com.epam.fitness.utils;

import javax.servlet.http.HttpServletRequest;

public class CurrentPageGetter {

    private static final String EMPTY_STRING = "";

    private CurrentPageGetter() {}

    public static String getCurrentPage(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        return requestURI.replace(contextPath, EMPTY_STRING);
    }
}