package com.epam.fitness.controller;

import org.junit.Assert;
import org.junit.Test;

public class ControllerUtilsTest {

    private static final String PAGE_TO_REDIRECT = "/order";
    private static final String EXPECTED_REDIRECT = "redirect:/order";
    private static final String PAGE_TO_FORWARD = "/client/list";
    private static final String EXPECTED_FORWARD = "forward:/client/list";

    @Test
    public void testCreateRedirect(){
        //given

        //when
        String actual = ControllerUtils.createRedirect(PAGE_TO_REDIRECT);

        //then
        Assert.assertEquals(EXPECTED_REDIRECT, actual);
    }

    @Test
    public void testCreateForward(){
        //given

        //when
        String actual = ControllerUtils.createForward(PAGE_TO_FORWARD);

        //then
        Assert.assertEquals(EXPECTED_FORWARD, actual);
    }

}
