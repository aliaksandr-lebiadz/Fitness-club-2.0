package com.epam.fitness.controller;

public class ControllerUtils {

    private static final String REDIRECT = "redirect:";
    private static final String FORWARD = "forward:";

    private ControllerUtils() {}

    public static String createRedirect(String page){
        return REDIRECT + page;
    }

    public static String createForward(String page){
        return FORWARD + page;
    }

}
