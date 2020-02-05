package com.epam.fitness.service.impl;

import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService service;
    private DtoMapper<User, UserDto> mapper;

    @Autowired
    public UserDetailsServiceImpl(UserService service, DtoMapper<User, UserDto> mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        UserDto userDto = getUserByEmail(email);
        User user = mapper.mapToEntity(userDto);
        String password = user.getPassword();
        Set<GrantedAuthority> authorities = getAuthorities(user);
        return new org.springframework.security.core.userdetails.User(email, password, authorities);
    }

    private UserDto getUserByEmail(String email) throws UsernameNotFoundException{
        try{
            return service.getUserByEmail(email);
        } catch (ServiceException ex){
            throw new UsernameNotFoundException(ex.getMessage(), ex);
        }
    }

    private Set<GrantedAuthority> getAuthorities(User user){
        UserRole userRole = user.getRole();
        Set<GrantedAuthority> authorities = new HashSet<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        authorities.add(authority);
        return authorities;
    }
}
