package com.epam.fitness.utils;

import javax.servlet.http.HttpServletRequest;

public class CurrentPageGetter {

    private static final String REFERER = "referer";

    private CurrentPageGetter() {}

    public static String getCurrentPage(HttpServletRequest request){
        return request.getHeader(REFERER);
    }
}