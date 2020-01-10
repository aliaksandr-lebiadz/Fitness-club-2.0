package com.epam.fitness.controller;

import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.service.api.GymMembershipService;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

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
    private static final String DISCOUNT_ATTRIBUTE = "discount";
    private static final String GET_MEMBERSHIP_PAGE_VIEW_NAME = "get_membership";
    private static final String ERROR_PAGE_URL = "/error";
    private static final String ERROR_PAGE_VIEW_NAME = "error_page";

    private static final List<GymMembership> EXPECTED_GYM_MEMBERSHIPS = Arrays.asList(
            new GymMembership(1, 1, BigDecimal.valueOf(40.21)),
            new GymMembership(2, 6, BigDecimal.valueOf(135.35)),
            new GymMembership(3, 12, BigDecimal.valueOf(251.63)));

    private static final int DISCOUNT = 55;
    private static final User USER = new User(1, "email", "pass", UserRole.CLIENT,
            "Alex", "Leb", DISCOUNT);

    @Autowired
    private GymMembershipService service;
    @Autowired
    private ControllerUtils utils;

    @Test
    @WithMockUser
    public void testGetHomePageWhenUserIsAuthorized() throws Exception{
        //given

        //when
        mockMvc.perform(get(HOME_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(HOME_PAGE_VIEW_NAME));

        //then
    }

    @Test
    @WithAnonymousUser
    public void testGetHomePageWhenUserIsNotAuthorized() throws Exception{
        //given

        //when
        mockMvc.perform(post(HOME_URL))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    public void testGetLoginPageWhenEmptyRequestSupplied() throws Exception{
        //given

        //when
        mockMvc.perform(get(EMPTY_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(LOGIN_PAGE_VIEW_NAME));

        //then
    }

    @Test
    public void testGetLoginPageWhenLoginRequestSupplied() throws Exception{
        //given

        //when
        mockMvc.perform(get(LOGIN_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(LOGIN_PAGE_VIEW_NAME));

        //then
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    public void testGetOrderPageWhenUserIsAuthorizedAsClient() throws Exception{
        //given
        when(service.getAll()).thenReturn(EXPECTED_GYM_MEMBERSHIPS);
        when(utils.getCurrentUser()).thenReturn(USER);

        //when
        mockMvc.perform(get(ORDER_URL))
                .andExpect(status().isOk())
                .andExpect(model().size(2))
                .andExpect(model().attributeExists(GYM_MEMBERSHIPS_ATTRIBUTE, DISCOUNT_ATTRIBUTE))
                .andExpect(model().attribute(GYM_MEMBERSHIPS_ATTRIBUTE, EXPECTED_GYM_MEMBERSHIPS))
                .andExpect(model().attribute(DISCOUNT_ATTRIBUTE, DISCOUNT))
                .andExpect(view().name(GET_MEMBERSHIP_PAGE_VIEW_NAME));

        //then
        verify(service, times(1)).getAll();
        verify(utils, times(1)).getCurrentUser();
    }

    @Test
    @WithMockUser(authorities = { "TRAINER", "ADMIN" })
    public void testGetOrderPageShouldRedirectOnErrorPageWhenUserIsNotAuthorizedAsClient() throws Exception{
        //given

        //when
        mockMvc.perform(get(ORDER_URL))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    public void testGetErrorPage() throws Exception{
        //given

        //when
        mockMvc.perform(get(ERROR_PAGE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR_PAGE_VIEW_NAME));

        //then
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(service, utils);
    }

}