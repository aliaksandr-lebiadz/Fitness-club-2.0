package com.epam.fitness.service.impl;

import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.EntityMappingException;
import com.epam.fitness.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
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
        Optional<UserDto> userDtoOptional = service.findUserByEmail(email);
        if(userDtoOptional.isPresent()){
            UserDto userDto = userDtoOptional.get();
            User user = mapToEntity(userDto);
            String userEmail = user.getEmail();
            String userPassword = user.getPassword();
            Set<GrantedAuthority> authorities = getAuthorities(user);
            return new org.springframework.security.core.userdetails.User(userEmail, userPassword, authorities);
        } else{
            throw new UsernameNotFoundException("User with email " + email + " not found!");
        }
    }

    private Set<GrantedAuthority> getAuthorities(User user){
        UserRole userRole = user.getRole();
        Set<GrantedAuthority> authorities = new HashSet<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        authorities.add(authority);
        return authorities;
    }

    private User mapToEntity(UserDto userDto){
        try{
            return mapper.mapToEntity(userDto);
        } catch (EntityMappingException ex){
            throw new UsernameNotFoundException(ex.getMessage(), ex);
        }
    }
}
