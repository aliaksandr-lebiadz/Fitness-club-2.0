package com.epam.fitness.controller;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.GymMembershipDto;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class SimplePagesControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String HOME_PAGE_VIEW_NAME = "home";
    private static final String LOGIN_PAGE_VIEW_NAME = "login";
    private static final String SIGN_UP_PAGE_VIEW_NAME = "sign_up";
    private static final String GET_MEMBERSHIP_VIEW_NAME = "get_membership";
    private static final String ERROR_PAGE_VIEW_NAME = "error_page";

    private static final String EMPTY_URL = "/";
    private static final String LOGIN_URL = "/login";
    private static final String ERROR_PAGE_URL = "/error";
    private static final String SIGN_UP_URL = "/sign-up";
    private static final String HOME_URL = "/home";
    private static final String ORDER_URL = "/order";

    private static final String LOGIN_FAIL_PARAM = "login_fail";
    private static final String SIGN_UP_FAIL_PARAM = "sign_up_fail";

    private static final User CLIENT = new User(1, "client@gmail.com", "pass", UserRole.CLIENT, "first-name", "second-name", 15);
    private static final GymMembership GYM_MEMBERSHIP = new GymMembership(1, 3, BigDecimal.TEN);
    private static final GymMembershipDto GYM_MEMBERSHIP_DTO = new GymMembershipDto(GYM_MEMBERSHIP.getId(), GYM_MEMBERSHIP.getMonthsAmount(), GYM_MEMBERSHIP.getPrice());

    @Autowired
    private UserDao userDao;

    @Autowired
    private Dao<GymMembership> gymMembershipDao;

    @Test
    @WithMockUser(username = "client@gmail.com")
    public void getIndexPage_withAuthorizedUser_homePageView() throws Exception{
        //given
        final String email = "client@gmail.com";
        when(userDao.findUserByEmail(email)).thenReturn(Optional.of(CLIENT));

        //when
        mockMvc.perform(get(EMPTY_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(HOME_PAGE_VIEW_NAME));

        //then
        unwrapAndVerify(userDao, times(1)).findUserByEmail(email);
    }

    @Test
    @WithAnonymousUser
    public void getIndexPage_withAnonymousUser_loginPageView() throws Exception {
        //given

        //when
        mockMvc.perform(get(EMPTY_URL))
                .andExpect(redirectedUrl(LOGIN_URL));

        //then
    }

    @Test
    @WithMockUser
    public void getLoginPage_withAuthorizedUser_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(get(LOGIN_URL))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithAnonymousUser
    public void getLoginPage_withAnonymousUserAndWithoutLoginFail_loginPageView() throws Exception {
        //given

        //when
        mockMvc.perform(get(LOGIN_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(LOGIN_PAGE_VIEW_NAME));

        //then
    }

    @Test
    @WithAnonymousUser
    public void getLoginPage_withAnonymousUserAndWithLoginFail_loginPageViewWithLFailParam() throws Exception {
        //given
        final boolean loginFailParam = true;

        //when
        mockMvc.perform(get(LOGIN_URL).param(LOGIN_FAIL_PARAM, String.valueOf(loginFailParam)))
                .andExpect(status().isOk())
                .andExpect(model().attribute(LOGIN_FAIL_PARAM, String.valueOf(loginFailParam)))
                .andExpect(view().name(LOGIN_PAGE_VIEW_NAME));

        //then
    }

    @Test
    @WithMockUser
    public void getSignUpPage_withAuthorizedUser_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(get(SIGN_UP_URL))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithAnonymousUser
    public void getSignUpPage_withAnonymousUserAndWithoutSignUpFail_signUpPageView() throws Exception {
        //given

        //when
        mockMvc.perform(get(SIGN_UP_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(SIGN_UP_PAGE_VIEW_NAME));

        //then
    }

    @Test
    @WithAnonymousUser
    public void getSignUpPage_withAnonymousUserAndWithSignUpFail_signUpPageViewWithFailParam() throws Exception {
        //given
        final boolean signUpFailParam = true;

        //when
        mockMvc.perform(get(SIGN_UP_URL).param(SIGN_UP_FAIL_PARAM, String.valueOf(signUpFailParam)))
                .andExpect(status().isOk())
                .andExpect(model().attribute(SIGN_UP_FAIL_PARAM, String.valueOf(signUpFailParam)))
                .andExpect(view().name(SIGN_UP_PAGE_VIEW_NAME));

        //then
    }

    @Test
    @WithAnonymousUser
    public void getHomePage_withAnonymousUser_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(get(HOME_URL))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser
    public void getHomePage_withAuthorizedUser_homePageView() throws Exception {
        //given

        //when
        mockMvc.perform(get(HOME_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(HOME_PAGE_VIEW_NAME));

        //then
    }

    @Test
    @WithMockUser(authorities = "TRAINER")
    public void getOrderPage_withTrainerRole_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(get(ORDER_URL))
                .andExpect(redirectedUrl(ERROR_PAGE_URL));

        //then
    }

    @Test
    @WithMockUser(username = "client@gmail.com", authorities = "CLIENT")
    public void getOrderPage_withClientRole_getMembershipPageView() throws Exception {
        //given
        when(userDao.findUserByEmail("client@gmail.com")).thenReturn(Optional.of(CLIENT));
        when(gymMembershipDao.getAll()).thenReturn(Collections.singletonList(GYM_MEMBERSHIP));

        //when
        mockMvc.perform(get(ORDER_URL))
                .andExpect(status().isOk())
                .andExpect(model().attribute("discount", CLIENT.getDiscount()))
                .andExpect(model().attribute("gymMembershipList", hasSize(1)))
                .andExpect(model().attribute("gymMembershipList", hasItem(GYM_MEMBERSHIP_DTO)))
                .andExpect(view().name(GET_MEMBERSHIP_VIEW_NAME));

        //then
        unwrapAndVerify(gymMembershipDao, times(1)).getAll();
        unwrapAndVerify(userDao, times(1)).findUserByEmail(CLIENT.getEmail());
    }

    @Test
    public void getErrorPage_withoutParameters_errorPageView() throws Exception {
        //given

        //when
        mockMvc.perform(get(ERROR_PAGE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR_PAGE_VIEW_NAME));

        //then
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(userDao, gymMembershipDao);
        reset(userDao, gymMembershipDao);
    }
}