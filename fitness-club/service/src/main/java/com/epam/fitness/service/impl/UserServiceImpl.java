package com.epam.fitness.service.impl;

import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.EntityMappingException;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserDao dao;
    private DtoMapper<User, UserDto> mapper;

    @Autowired
    public UserServiceImpl(UserDao dao, DtoMapper<User, UserDto> mapper){
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public List<UserDto> getClientsOfTrainer(UserDto trainerDto) throws ServiceException{
        try{
            User trainer = mapper.mapToEntity(trainerDto);
            List<User> clients =  dao.findClientsOfTrainer(trainer);
            return clients.stream()
                    .map(client -> mapper.mapToDto(client))
                    .collect(Collectors.toList());
        } catch (EntityMappingException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<UserDto> getAllClients() {
        List<User> clients = dao.getAllClients();
        return clients.stream()
                .map(user -> mapper.mapToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findUserByEmail(String email) {
        Optional<User> userOptional = dao.findUserByEmail(email);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            UserDto userDto = mapper.mapToDto(user);
            return Optional.of(userDto);
        } else{
            return Optional.empty();
        }
    }

    @Override
    public void update(UserDto userDto) throws ServiceException {
        try{
            User user = mapper.mapToEntity(userDto);
            dao.save(user);
        } catch (EntityMappingException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

}
