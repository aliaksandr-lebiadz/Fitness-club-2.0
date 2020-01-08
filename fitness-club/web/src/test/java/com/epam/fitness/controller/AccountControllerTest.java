package com.epam.fitness.controller;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.service.api.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class AccountControllerTest extends AbstractControllerTest{

    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String VALID_USER_EMAIL = "admin@gmail.com";
    private static final String VALID_USER_PASSWORD = "admin";
    private static final String INVALID_USER_EMAIL = "user@gmail.com";
    private static final String INVALID_USER_PASSWORD = "user";
    private static final User EXPECTED_USER = new User(5, VALID_USER_EMAIL, VALID_USER_PASSWORD,
            UserRole.CLIENT, "firstName", "secondName", 55);
    private static final Locale EXPECTED_LOCALE = new Locale("en", "US");
    private static final String USER_ATTRIBUTE = "user";
    private static final String LOGIN_FAIL_ATTRIBUTE = "login_fail";
    private static final String LOGIN_REQUEST = "/account/logIn";
    private static final String LOGOUT_REQUEST = "/account/logOut";
    private static final String HOME_PAGE_URL = "/home";
    private static final String LOGIN_PAGE_URL = "/login";
    private static final String LOCALE_ATTRIBUTE = "locale";

    @Autowired
    private UserService service;

    @Before
    public void createMocks(){
        when(service.login(INVALID_USER_EMAIL, INVALID_USER_PASSWORD)).thenReturn(Optional.empty());
        when(service.login(VALID_USER_EMAIL, VALID_USER_PASSWORD)).thenReturn(Optional.of(EXPECTED_USER));
    }

    @Test
    public void testLogInWhenSignedUpUserSupplied() throws Exception{
        HttpSession session = mockMvc.perform(post(LOGIN_REQUEST)
                .param(EMAIL_PARAMETER, VALID_USER_EMAIL)
                .param(PASSWORD_PARAMETER, VALID_USER_PASSWORD))
                .andExpect(redirectedUrl(HOME_PAGE_URL))
                .andReturn()
                .getRequest()
                .getSession();
        Assert.assertNotNull(session);

        User actual = (User) session.getAttribute(USER_ATTRIBUTE);
        Assert.assertEquals(EXPECTED_USER, actual);

        verify(service, times(1)).login(VALID_USER_EMAIL, VALID_USER_PASSWORD);
    }

    @Test
    public void testLogInWhenNotSignedUpUserSupplied() throws Exception{
        HttpSession session = mockMvc.perform(post(LOGIN_REQUEST)
                .param(EMAIL_PARAMETER, INVALID_USER_EMAIL)
                .param(PASSWORD_PARAMETER, INVALID_USER_PASSWORD))
                .andExpect(redirectedUrl(LOGIN_PAGE_URL))
                .andReturn()
                .getRequest()
                .getSession();
        Assert.assertNotNull(session);

        boolean actual = (Boolean) session.getAttribute(LOGIN_FAIL_ATTRIBUTE);
        Assert.assertTrue(actual);

        verify(service, times(1)).login(INVALID_USER_EMAIL, INVALID_USER_PASSWORD);
    }

    @Test
    public void testLogOut() throws Exception{
        HttpSession session = mockMvc.perform(get(LOGOUT_REQUEST)
                .sessionAttr(USER_ATTRIBUTE, EXPECTED_USER)
                .sessionAttr(LOCALE_ATTRIBUTE, EXPECTED_LOCALE))
                .andExpect(redirectedUrl(LOGIN_PAGE_URL))
                .andReturn()
                .getRequest()
                .getSession();
        Assert.assertNotNull(session);

        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        Assert.assertNull(user);

        Locale actual = (Locale) session.getAttribute(LOCALE_ATTRIBUTE);
        Assert.assertEquals(EXPECTED_LOCALE, actual);
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(service);
    }

}