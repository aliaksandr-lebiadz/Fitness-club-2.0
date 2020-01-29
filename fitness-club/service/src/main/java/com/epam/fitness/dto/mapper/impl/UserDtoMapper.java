package com.epam.fitness.dto.mapper.impl;

import com.epam.fitness.dto.mapper.AbstractDtoMapper;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper extends AbstractDtoMapper<User, UserDto> {

    @Autowired
    public UserDtoMapper(ModelMapper modelMapper){
        super(modelMapper, User.class, UserDto.class);
    }
}
