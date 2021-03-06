package com.epam.fitness.service.impl;

import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.CredentialsDto;
import com.epam.fitness.entity.SortOrder;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.EntityAlreadyExistsException;
import com.epam.fitness.exception.EntityNotFoundException;
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
                .orElseThrow(() -> new EntityNotFoundException("Trainer with id " + trainerId + " not found!"));
        List<User> clients =  dao.findClientsOfTrainer(trainer);
        return mapper.mapToDto(clients);
    }

    @Override
    public List<UserDto> getAllClients() throws ServiceException{
        List<User> clients = dao.getAllClients();
        return mapper.mapToDto(clients);
    }

    @Override
    public List<UserDto> searchUsersByParameters(String firstName, String secondName, String email,
                                                 SortOrder order)
            throws ServiceException{
        List<User> users = dao.findUsersByParameters(firstName, secondName, email, order);
        return mapper.mapToDto(users);
    }

    @Override
    public UserDto getUserByEmail(String email) throws ServiceException {
        Optional<User> userOptional = dao.findUserByEmail(email);
        User user = userOptional
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found!"));
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
    public UserDto updateById(int id, UserDto userDto) throws ServiceException {
        User user = mapper.mapToEntity(userDto);
        Optional<User> userOptional = dao.findById(id);
        User oldUser = userOptional
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found!"));
        List<Order> orders = oldUser.getOrders();
        user.setOrders(orders);
        String password = user.getPassword();
        String passwordHash = hashPassword(password);
        user.setPassword(passwordHash);
        user.setId(id);
        User savedUser = dao.save(user);
        return mapper.mapToDto(savedUser);
    }

    @Override
    public UserDto create(UserDto userDto) throws ServiceException{
        Integer id = userDto.getId();
        if(Objects.nonNull(id)){
            Optional<User> userOptional = dao.findById(id);
            if(userOptional.isPresent()){
                throw new EntityAlreadyExistsException("User with id " + id + " already exists!");
            }
        }
        User user = mapper.mapToEntity(userDto);
    	String password = user.getPassword();
		String passwordHash = hashPassword(password);
		user.setPassword(passwordHash);
		User savedUser = dao.save(user);
        return mapper.mapToDto(savedUser);
    }

    @Override
    public UserDto getById(int id) throws ServiceException {
        Optional<User> userOptional = dao.findById(id);
        User user = userOptional
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found!"));
        return mapper.mapToDto(user);
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        Optional<User> userOptional = dao.findById(id);
        if(!userOptional.isPresent()){
            throw new EntityNotFoundException("User with id " + id + " not found!");
        }
        dao.deleteById(id);
    }
	
	private String hashPassword(String password) {
		return DigestUtils.md5Hex(password);
	}

}
