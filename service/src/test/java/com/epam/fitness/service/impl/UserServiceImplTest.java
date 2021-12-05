package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.SignUpRequestDto;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.EntityMappingException;
import com.epam.fitness.exception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String EXISTENT_EMAIL = "admin@gmail.com";
    private static final String NONEXISTENT_EMAIL = "client@gmail.com";
    private static final User USER = new User(5, EXISTENT_EMAIL, "admin", UserRole.ADMIN,
            "Alex", "Tuhev", 15);
    private static final UserDto USER_DTO_WITH_EXISTENT_ID = new UserDto(5, EXISTENT_EMAIL, "admin", UserRole.ADMIN,
            "Alex", "Tuhev", 15);
    private static final UserDto USER_DTO_WITH_NONEXISTENT_ID =
            new UserDto(103, "user@gmail.com", "user", UserRole.CLIENT,
                    "Mikhail", "Olev", 10);

    private static final List<User> USERS = Collections.singletonList(USER);
    private static final List<UserDto> USER_DTOS = Collections.singletonList(USER_DTO_WITH_EXISTENT_ID);
    private static final User TRAINER = new User(3, "trainer@gmail.com", "trainer", UserRole.TRAINER,
            "Ivan", "Feoktistov", 10);
    private static final UserDto TRAINER_DTO = new UserDto(3, "trainer@gmail.com", "trainer", UserRole.TRAINER,
            "Ivan", "Feoktistov", 10);

    @Mock
    private UserDao userDao;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private DtoMapper<User, UserDto> userDtoMapper;
    @InjectMocks
    private UserServiceImpl service;

    @Before
    public void createMocks() throws EntityMappingException {
        when(userDao.findUserByEmail(EXISTENT_EMAIL)).thenReturn(Optional.of(USER));
        when(userDao.findUserByEmail(NONEXISTENT_EMAIL)).thenReturn(Optional.empty());
        when(userDtoMapper.mapToDto(USER)).thenReturn(USER_DTO_WITH_EXISTENT_ID);
        when(userDtoMapper.mapToEntity(USER_DTO_WITH_EXISTENT_ID)).thenReturn(USER);

        when(userDao.getAll()).thenReturn(USERS);

        when(userDtoMapper.mapToEntity(TRAINER_DTO)).thenReturn(TRAINER);
        when(userDao.findClientsOfTrainer(TRAINER)).thenReturn(USERS);

        when(userDtoMapper.mapToEntity(USER_DTO_WITH_NONEXISTENT_ID)).thenThrow(EntityMappingException.class);
        when(userDao.save(USER)).thenReturn(USER);
    }

    @Test
    public void findUserByEmail_withExistentEmail_foundUser(){
        //given

        //when
        Optional<UserDto> actualOptional = service.findUserByEmail(EXISTENT_EMAIL);

        //then
        Assert.assertTrue(actualOptional.isPresent());
        UserDto actual = actualOptional.get();
        assertEquals(USER_DTO_WITH_EXISTENT_ID, actual);

        verify(userDao, times(1)).findUserByEmail(EXISTENT_EMAIL);
        verify(userDtoMapper, times(1)).mapToDto(USER);
        verifyNoMoreInteractions(userDao, userDtoMapper);
    }

    @Test
    public void findUserByEmail_withNonexistentEmail_emptyOptional(){
        //given

        //when
        Optional<UserDto> actualOptional = service.findUserByEmail(NONEXISTENT_EMAIL);

        //then
        Assert.assertFalse(actualOptional.isPresent());

        verify(userDao, times(1)).findUserByEmail(NONEXISTENT_EMAIL);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void getAll_withExistentUsers_listOfUsers(){
        //given

        //when
        List<UserDto> actual = service.getAll();

        //then
        assertThat(actual, is(equalTo(USER_DTOS)));

        verify(userDao, times(1)).getAll();
        verify(userDtoMapper, times(1)).mapToDto(USER);
        verifyNoMoreInteractions(userDao, userDtoMapper);
    }

    @Test
    public void getClientsOfTrainer_withExistentClients_trainerClients() throws ServiceException, EntityMappingException{
        //given

        //when
        List<UserDto> actual = service.getClientsOfTrainer(TRAINER_DTO);

        //then
        assertThat(actual, is(equalTo(USER_DTOS)));

        verify(userDtoMapper, times(1)).mapToEntity(TRAINER_DTO);
        verify(userDtoMapper, times(1)).mapToDto(USER);
        verify(userDao, times(1)).findClientsOfTrainer(TRAINER);
        verifyNoMoreInteractions(userDao, userDtoMapper);
    }

    @Test
    public void update_withExistentUserId_userSuccessfullyUpdated() throws ServiceException, EntityMappingException {
        //given

        //when
        service.update(USER_DTO_WITH_EXISTENT_ID);

        //then
        verify(userDao, times(1)).save(USER);
        verify(userDtoMapper, times(1)).mapToEntity(USER_DTO_WITH_EXISTENT_ID);
        verifyNoMoreInteractions(userDao, userDtoMapper);
    }

    @Test(expected = ServiceException.class)
    public void update_withNonexistentUserId_serviceException()
            throws ServiceException, EntityMappingException{
        //given

        //when
        service.update(USER_DTO_WITH_NONEXISTENT_ID);

        //then
        verify(userDtoMapper, times(1)).mapToEntity(USER_DTO_WITH_NONEXISTENT_ID);
        verifyNoMoreInteractions(userDtoMapper);
    }

    @Test(expected = ServiceException.class)
    public void signUp_requestWithExistentEmail_serviceException() throws ServiceException {
        //given
        final SignUpRequestDto signUpRequest = new SignUpRequestDto(EXISTENT_EMAIL, "pass", "first-name", "last-name", UserRole.CLIENT);

        //when
        service.signUp(signUpRequest);

        //then
    }

    @Test
    public void signUp_requestWithNonexistentEmail_savedUser() throws ServiceException {
        //given
        final String password = "pass";
        final String firstName = "Alex";
        final String secondName = "Lomov";
        final String encodedPassword = "encoded-password";
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        User user = new User(null, NONEXISTENT_EMAIL, encodedPassword, UserRole.TRAINER, firstName, secondName, 0);
        UserDto userDto = new UserDto(null, NONEXISTENT_EMAIL, encodedPassword, UserRole.TRAINER, firstName, secondName, 0);
        when(userDao.save(user)).thenReturn(user);
        when(userDtoMapper.mapToDto(user)).thenReturn(userDto);

        final SignUpRequestDto signUpRequest = new SignUpRequestDto(NONEXISTENT_EMAIL, password, firstName, secondName, UserRole.TRAINER);

        //when
        UserDto actual = service.signUp(signUpRequest);

        //then
        assertEquals(encodedPassword, actual.getPassword());
        assertEquals(firstName, actual.getFirstName());
        assertEquals(secondName, actual.getSecondName());

        verify(userDao, times(1)).findUserByEmail(NONEXISTENT_EMAIL);
        verify(userDao, times(1)).save(eq(user));
        verify(userDtoMapper, times(1)).mapToDto(user);
        verifyNoMoreInteractions(userDao, userDtoMapper);
    }

}