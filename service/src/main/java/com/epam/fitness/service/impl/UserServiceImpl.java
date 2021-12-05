package com.epam.fitness.service.impl;

import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.SignUpRequestDto;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.EntityMappingException;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;
    private UserDao dao;
    private DtoMapper<User, UserDto> mapper;

    @Autowired
    public UserServiceImpl(UserDao dao, DtoMapper<User, UserDto> mapper, PasswordEncoder passwordEncoder){
        this.dao = dao;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
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
    public List<UserDto> getAll() {
        List<User> users = dao.getAll();
        return users.stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findUserByEmail(String email) {
        Optional<User> userOptional = dao.findUserByEmail(email);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            UserDto userDto = mapper.mapToDto(user);
            return Optional.of(userDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public UserDto signUp(SignUpRequestDto signUpRequestDto) throws ServiceException {
        String email = signUpRequestDto.getEmail();
        Optional<User> optionalUser = dao.findUserByEmail(email);
        if(optionalUser.isPresent()) {
            throw new ServiceException("User with email " + email + " already exists!");
        }
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        User user = new User(null, email, encodedPassword, signUpRequestDto.getRole(), signUpRequestDto.getFirstName(), signUpRequestDto.getSecondName(), 0);
        User savedUser = dao.save(user);
        return mapper.mapToDto(savedUser);
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
