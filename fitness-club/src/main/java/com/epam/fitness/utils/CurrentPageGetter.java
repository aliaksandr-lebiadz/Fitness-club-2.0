package com.epam.fitness.utils;

import javax.servlet.http.HttpServletRequest;

public class CurrentPageGetter {

    private static final String REFERER = "referer";
    private static final String EMPTY_STRING = "";
    private static final String SEPARATOR_BETWEEN_SCHEME_AND_SERVER_NAME = "://";
    private static final String SEPARATOR_BETWEEN_SERVER_NAME_AND_PORT = ":";

    public static String getCurrentPage(HttpServletRequest request){
        String header = request.getHeader(REFERER);
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String serverUrl = scheme + SEPARATOR_BETWEEN_SCHEME_AND_SERVER_NAME + serverName +
                SEPARATOR_BETWEEN_SERVER_NAME_AND_PORT + serverPort + contextPath;
        return header.replace(serverUrl, EMPTY_STRING);
    }

}