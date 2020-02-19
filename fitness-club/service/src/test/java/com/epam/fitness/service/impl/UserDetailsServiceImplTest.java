package com.epam.fitness.service.impl;

import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {

    private static final String NONEXISTENT_EMAIL = "t@gmail.com";
    private static final String EXISTENT_EMAIL = "sasha@gmail.com";
    private static final String EXPECTED_PASSWORD = "alex";
    private static final UserDto USER_DTO = new UserDto(5, EXISTENT_EMAIL, EXPECTED_PASSWORD, UserRole.CLIENT,
            "Nikolay", "Aleev", 67);
    private static final User USER = new User(5, EXISTENT_EMAIL, EXPECTED_PASSWORD, UserRole.CLIENT,
            "Nikolay", "Aleev", 67);
    private static final Set<GrantedAuthority> EXPECTED_AUTHORITIES =
            Collections.singleton(new SimpleGrantedAuthority("CLIENT"));

    @Mock
    private UserService userService;
    @Mock
    private DtoMapper<User, UserDto> userMapper;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Before
    public void createMocks() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        when(userService.getUserByEmail(NONEXISTENT_EMAIL)).thenThrow(ServiceException.class);
        when(userService.getUserByEmail(EXISTENT_EMAIL)).thenReturn(USER_DTO);
        when(userMapper.mapToEntity(USER_DTO)).thenReturn(USER);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameWithUserNameNotFoundException() throws ServiceException{
        //given

        //when/then
        try{
            userDetailsService.loadUserByUsername(NONEXISTENT_EMAIL);
        } finally {
            verify(userService).getUserByEmail(NONEXISTENT_EMAIL);
        }
    }

    @Test
    public void loadUserByUserNameWhenExistentEmailSupplied() throws ServiceException{
        //given

        //when
        UserDetails actual = userDetailsService.loadUserByUsername(EXISTENT_EMAIL);

        //then
        assertEquals(EXISTENT_EMAIL, actual.getUsername());
        assertEquals(EXPECTED_PASSWORD, actual.getPassword());
        assertEquals(EXPECTED_AUTHORITIES, actual.getAuthorities());

        verify(userService).getUserByEmail(EXISTENT_EMAIL);
        verify(userMapper).mapToEntity(USER_DTO);
    }

    @After
    public void verifyMocks(){
        verifyNoMoreInteractions(userService, userMapper);
        reset(userService, userMapper);
    }

}