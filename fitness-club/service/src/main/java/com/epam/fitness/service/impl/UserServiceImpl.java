package com.epam.fitness.service.impl;

import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.CredentialsDto;
import com.epam.fitness.entity.SortOrder;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao dao;
    private DtoMapper<User, UserDto> mapper;

    @Autowired
    public UserServiceImpl(UserDao dao, DtoMapper<User, UserDto> mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public List<UserDto> getClientsByTrainerId(int trainerId) throws ServiceException{
        Optional<User> trainerOptional = dao.findById(trainerId);
        User trainer = trainerOptional
                .orElseThrow(() -> new ServiceException("Trainer with id " + trainerId + " not found!"));
        List<User> clients =  dao.findClientsOfTrainer(trainer);
        return mapper.mapToDto(clients);
    }

    @Override
    public List<UserDto> getAllClients() {
        List<User> clients = dao.getAllClients();
        return mapper.mapToDto(clients);
    }

    @Override
    public List<UserDto> searchUsersByParameters(String firstName, String secondName, String email, SortOrder order) {
        List<User> users = dao.findUsersByParameters(firstName, secondName, email, order);
        return mapper.mapToDto(users);
    }

    @Override
    public UserDto getUserByEmail(String email) throws ServiceException {
        Optional<User> userOptional = dao.findUserByEmail(email);
        User user = userOptional
                .orElseThrow(() -> new ServiceException("User with email " + email + " not found!"));
        return mapper.mapToDto(user);
    }

    @Override
    public UserDto getUserWithCredentials(CredentialsDto credentialsDto) throws ServiceException {
        String email = credentialsDto.getEmail();
        String password = credentialsDto.getPassword();
        Optional<User> userOptional = dao.findUserByEmailAndPassword(email, DigestUtils.md5Hex(password));
        User user = userOptional
                .orElseThrow(() -> new ServiceException("Login failed!"));
        return mapper.mapToDto(user);
    }

    @Override
    public void updateById(int id, UserDto userDto) throws ServiceException {
        Optional<User> userOptional = dao.findById(id);
        User user = userOptional
                .orElseThrow(() -> new ServiceException("User with id " + id + " not found!"));
        Integer discount = userDto.getDiscount();
        if(Objects.nonNull(discount)){
            user.setDiscount(discount);
            dao.save(user);
        }
    }

    @Override
    public void create(UserDto userDto) {
        User user = mapper.mapToEntity(userDto);
		String password = user.getPassword();
		String passwordHash = hashPassword(password);
		user.setPassword(passwordHash);
        dao.save(user);
    }

    @Override
    public UserDto getById(int id) throws ServiceException {
        Optional<User> userOptional = dao.findById(id);
        User user = userOptional
                .orElseThrow(() -> new ServiceException("User with id " + id + " not found!"));
        return mapper.mapToDto(user);
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        Optional<User> userOptional = dao.findById(id);
        User user = userOptional
                .orElseThrow(() -> new ServiceException("User with id " + id + " not found!"));
        dao.delete(user);
    }
	
	private String hashPassword(String password) {
		return DigestUtils.md5Hex(password);
	}

}
