package com.epam.fitness.controller;

import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.service.api.GymMembershipService;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class SimplePagesControllerTest extends AbstractControllerTest{

    private static final String HOME_URL = "/home";
    private static final String HOME_PAGE_VIEW_NAME = "home";
    private static final String EMPTY_URL = "/";
    private static final String LOGIN_URL = "/login";
    private static final String LOGIN_PAGE_VIEW_NAME = "login";
    private static final String ORDER_URL = "/order";
    private static final String GYM_MEMBERSHIPS_ATTRIBUTE = "gymMembershipList";
    private static final String GET_MEMBERSHIP_PAGE_VIEW_NAME = "get_membership";
    private static final String ERROR_PAGE_URL = "/error";
    private static final String ERROR_PAGE_VIEW_NAME = "error_page";
    private static final String ERROR_PAGE_404_URL = "/error404";
    private static final String ERROR_PAGE_404_VIEW_NAME = "404_error_page";

    private static final List<GymMembership> EXPECTED_GYM_MEMBERSHIPS = Arrays.asList(
            new GymMembership(1, 1, BigDecimal.valueOf(40.21)),
            new GymMembership(2, 6, BigDecimal.valueOf(135.35)),
            new GymMembership(3, 12, BigDecimal.valueOf(251.63)));

    @Autowired
    private GymMembershipService service;

    @Test
    public void testGetHomePage() throws Exception{
        mockMvc.perform(get(HOME_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(HOME_PAGE_VIEW_NAME));
    }

    @Test
    public void testGetLoginPageWhenEmptyRequestSupplied() throws Exception{
        mockMvc.perform(get(EMPTY_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(LOGIN_PAGE_VIEW_NAME));
    }

    @Test
    public void testGetLoginPageWhenLoginRequestSupplied() throws Exception{
        mockMvc.perform(get(LOGIN_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(LOGIN_PAGE_VIEW_NAME));
    }

    @Test
    public void testGetOrderPage() throws Exception{
        when(service.getAll()).thenReturn(EXPECTED_GYM_MEMBERSHIPS);
        mockMvc.perform(get(ORDER_URL))
                .andExpect(status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists(GYM_MEMBERSHIPS_ATTRIBUTE))
                .andExpect(model().attribute(GYM_MEMBERSHIPS_ATTRIBUTE, EXPECTED_GYM_MEMBERSHIPS))
                .andExpect(view().name(GET_MEMBERSHIP_PAGE_VIEW_NAME));

        verify(service, times(1)).getAll();
    }

    @Test
    public void testGetErrorPage() throws Exception{
        mockMvc.perform(get(ERROR_PAGE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR_PAGE_VIEW_NAME));
    }

    @Test
    public void testGet404ErrorPage() throws Exception{
        mockMvc.perform(get(ERROR_PAGE_404_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR_PAGE_404_VIEW_NAME));
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(service);
    }

}