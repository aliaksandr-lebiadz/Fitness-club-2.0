package com.epam.fitness.dto.mapper.impl;

import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.dto.mapper.AbstractDtoMapper;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper extends AbstractDtoMapper<User, UserDto> {

    @Autowired
    public UserDtoMapper(ModelMapper modelMapper, UserDao userDao){
        super(modelMapper, userDao, UserDto.class);
    }

    @Override
    protected void setMutableFields(UserDto source, User destination){
        destination.setDiscount(source.getDiscount());
    }

}
