package com.epam.fitness.service.impl;

import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.EntityMappingException;
import com.epam.fitness.service.api.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

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
    private DtoMapper<User, UserDto> userDtoMapper;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Before
    public void createMocks() throws EntityMappingException {
        when(userService.findUserByEmail(NONEXISTENT_EMAIL)).thenReturn(Optional.empty());
        when(userService.findUserByEmail(EXISTENT_EMAIL)).thenReturn(Optional.of(USER_DTO));
        when(userDtoMapper.mapToEntity(USER_DTO)).thenReturn(USER);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsernameShouldThrowsExceptionWhenNonexistentEmailSupplied(){
        //given

        //when
        userDetailsService.loadUserByUsername(NONEXISTENT_EMAIL);

        //then
        verify(userService, times(1)).findUserByEmail(NONEXISTENT_EMAIL);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testLoadUserByUsernameShouldReturnUserDetailsWhenExistentEmailSupplied()
            throws EntityMappingException{
        //given

        //when
        UserDetails actual = userDetailsService.loadUserByUsername(EXISTENT_EMAIL);

        //then
        Assert.assertEquals(EXISTENT_EMAIL, actual.getUsername());
        Assert.assertEquals(EXPECTED_PASSWORD, actual.getPassword());
        Assert.assertEquals(EXPECTED_AUTHORITIES, actual.getAuthorities());

        verify(userService, times(1)).findUserByEmail(EXISTENT_EMAIL);
        verify(userDtoMapper, times(1)).mapToEntity(USER_DTO);
        verifyNoMoreInteractions(userService, userDtoMapper);
    }

}