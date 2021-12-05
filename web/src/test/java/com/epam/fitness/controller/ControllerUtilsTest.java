package com.epam.fitness.controller;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ControllerUtilsTest {

    private static final String PAGE_TO_REDIRECT = "/order";
    private static final String EXPECTED_REDIRECT = "redirect:/order";
    private static final String PAGE_TO_FORWARD = "/client/list";
    private static final String EXPECTED_FORWARD = "forward:/client/list";

    @Test
    public void createRedirect_withPageToRedirect_redirectWithPage(){
        //given

        //when
        String actual = ControllerUtils.createRedirect(PAGE_TO_REDIRECT);

        //then
        assertEquals(EXPECTED_REDIRECT, actual);
    }

    @Test
    public void createForwrad_withPageToForward_forwardWithPage(){
        //given

        //when
        String actual = ControllerUtils.createForward(PAGE_TO_FORWARD);

        //then
        assertEquals(EXPECTED_FORWARD, actual);
    }

}
