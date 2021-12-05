package com.epam.fitness.controller;

import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String USER_URL = "/user";
    private static final String ERROR_URL = "/error";

    private static final String USERS_PAGE_VIEW_NAME = "users";

    private static final String EMAIL = "admin@gmail.com";
    private static final String PASSWORD = "qwerty12345";
    private static final String FIRST_NAME = "Alex";
    private static final String SECOND_NAME = "Lopov";
    private static final User CLIENT = new User(5, "client@gmail.com", "pass", UserRole.CLIENT, "client-name", "client-surname", 0);
    private static final UserDto CLIENT_DTO = new UserDto(5, "client@gmail.com", "pass", UserRole.CLIENT, "client-name", "client-surname", 0);
    private static final User CLIENT_FOR_UPDATE = new User(6, "client@gmail.com", "pass", UserRole.CLIENT, "client-name", "client-surname", 0);
    private static final User CLIENT_WITH_NEW_DISCOUNT = new User(6, "client@gmail.com", "pass", UserRole.CLIENT, "client-name", "client-surname", 50);

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(authorities = "CLIENT")
    public void getUsersPage_withClientRole_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(get(USER_URL + "/list"))
                .andExpect(redirectedUrl(ERROR_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void getUsersPage_withAdminRole_usersPageView() throws Exception {
        //given
        when(userDao.getAll()).thenReturn(Collections.singletonList(CLIENT));

        //when
        mockMvc.perform(get(USER_URL + "/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("userList", hasSize(1)))
                .andExpect(model().attribute("userList", hasItem(CLIENT_DTO)))
                .andExpect(view().name(USERS_PAGE_VIEW_NAME));

        //then
        unwrapAndVerify(userDao, times(1)).getAll();
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    public void setDiscount_withClientRole_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(get(USER_URL + "/set-discount")
                .param("user_id", String.valueOf(2))
                .param("discount", String.valueOf(50)))
                .andExpect(redirectedUrl(ERROR_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void setDiscount_withInvalidDiscount_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(get(USER_URL + "/set-discount")
                .param("user_id", String.valueOf(2))
                .param("discount", String.valueOf(101)))
                .andExpect(redirectedUrl(ERROR_URL));

        //then
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void setDiscount_withNonexistentUserId_redirectOnErrorPage() throws Exception {
        //given
        final int nonexistentUserId = 50;
        when(userDao.findById(nonexistentUserId)).thenReturn(Optional.empty());

        //when
        mockMvc.perform(post(USER_URL + "/set-discount")
                .param("user_id", String.valueOf(nonexistentUserId))
                .param("discount", String.valueOf(50)))
                .andExpect(redirectedUrl(ERROR_URL));

        //then
        unwrapAndVerify(userDao, times(1)).findById(nonexistentUserId);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void setDiscount_withExistentUserId_setDiscountAndRedirectOnUsersPage() throws Exception {
        //given
        final int existentUserId = CLIENT.getId();
        when(userDao.findById(existentUserId)).thenReturn(Optional.of(CLIENT_FOR_UPDATE));
        when(userDao.save(CLIENT_WITH_NEW_DISCOUNT)).thenReturn(CLIENT_WITH_NEW_DISCOUNT);

        //when
        mockMvc.perform(post(USER_URL + "/set-discount")
                .param("user_id", String.valueOf(existentUserId))
                .param("discount", String.valueOf(50)))
                .andExpect(redirectedUrl(USER_URL + "/list"));

        //then
        unwrapAndVerify(userDao, times(1)).findById(existentUserId);
        unwrapAndVerify(userDao, times(1)).save(CLIENT_WITH_NEW_DISCOUNT);
    }

    @Test
    @WithMockUser
    public void signUp_withAuthorizedUser_redirectOnErrorPage() throws Exception {
        //given

        //when
        mockMvc.perform(post(USER_URL + "/sign-up")
                .param("email", EMAIL)
                .param("password", PASSWORD)
                .param("first_name", FIRST_NAME)
                .param("second_name", SECOND_NAME))
                .andExpect(redirectedUrl(ERROR_URL));

        //then
    }

    @Test
    @WithAnonymousUser
    public void signUp_withInvalidEmail_redirectOnErrorPage() throws Exception {
        //given
        final String invalidEmail = "123";

        //when
        mockMvc.perform(post(USER_URL + "/sign-up")
                .param("email", invalidEmail)
                .param("password", PASSWORD)
                .param("first_name", FIRST_NAME)
                .param("second_name", SECOND_NAME))
                .andExpect(redirectedUrl(ERROR_URL));

        //then
    }

    @Test
    @WithAnonymousUser
    public void signUp_withInvalidPasswordLength_redirectOnErrorPage() throws Exception {
        //given
        final String invalidPassword = "pass";

        //when
        mockMvc.perform(post(USER_URL + "/sign-up")
                .param("email", EMAIL)
                .param("password", invalidPassword)
                .param("first_name", FIRST_NAME)
                .param("second_name", SECOND_NAME))
                .andExpect(redirectedUrl(ERROR_URL));

        //then
    }

    @Test
    @WithAnonymousUser
    public void signUp_withInvalidFirstNameLength_redirectOnErrorPage() throws Exception {
        //given
        final String invalidFirstName = "f";

        //when
        mockMvc.perform(post(USER_URL + "/sign-up")
                .param("email", EMAIL)
                .param("password", PASSWORD)
                .param("first_name", invalidFirstName)
                .param("second_name", SECOND_NAME))
                .andExpect(redirectedUrl(ERROR_URL));

        //then
    }

    @Test
    @WithAnonymousUser
    public void signUp_withInvalidSecondNameLength_redirectOnErrorPage() throws Exception {
        //given
        final String invalidSecondName = "s";

        //when
        mockMvc.perform(post(USER_URL + "/sign-up")
                .param("email", EMAIL)
                .param("password", PASSWORD)
                .param("first_name", FIRST_NAME)
                .param("second_name", invalidSecondName))
                .andExpect(redirectedUrl(ERROR_URL));

        //then
    }

    @Test
    @WithAnonymousUser
    public void signUp_withExistentEmail_redirectOnSignUpPageWithFailParam() throws Exception {
        //given
        final String existentEmail = "client@mail.ru";
        when(userDao.findUserByEmail(existentEmail)).thenReturn(Optional.of(CLIENT));

        //when
        mockMvc.perform(post(USER_URL + "/sign-up")
                .param("email", existentEmail)
                .param("password", PASSWORD)
                .param("first_name", FIRST_NAME)
                .param("second_name", SECOND_NAME))
                .andExpect(redirectedUrl("/sign-up?sign_up_fail"));

        //then
        unwrapAndVerify(userDao, times(1)).findUserByEmail(existentEmail);
    }

    @Test
    @WithAnonymousUser
    public void signUp_withNonexistentEmail_createUserAndRedirectOnLoginPage() throws Exception {
        //given
        final String nonexistentEmail = "client1@mail.ru";
        final User user = new User(null, nonexistentEmail, PASSWORD, UserRole.CLIENT, FIRST_NAME, SECOND_NAME, 0);
        when(userDao.findUserByEmail(nonexistentEmail)).thenReturn(Optional.empty());
        when(userDao.save(user)).thenReturn(user);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);

        //when
        mockMvc.perform(post(USER_URL + "/sign-up")
                .param("email", nonexistentEmail)
                .param("password", PASSWORD)
                .param("first_name", FIRST_NAME)
                .param("second_name", SECOND_NAME))
                .andExpect(redirectedUrl("/login"));

        //then
        unwrapAndVerify(userDao, times(1)).findUserByEmail(nonexistentEmail);
        unwrapAndVerify(userDao, times(1)).save(user);
        unwrapAndVerify(passwordEncoder, times(1)).encode(PASSWORD);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(userDao, passwordEncoder);
        reset(userDao, passwordEncoder);
    }
}
