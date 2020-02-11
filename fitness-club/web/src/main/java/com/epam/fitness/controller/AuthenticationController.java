package com.epam.fitness.controller;

import com.epam.fitness.entity.CredentialsDto;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticator")
public class AuthenticationController {

    private UserService service;

    @Autowired
    public AuthenticationController(UserService service){
        this.service = service;
    }

    @PostMapping
    public UserDto login(@RequestBody CredentialsDto credentialsDto) throws ServiceException {
        return service.getUserWithCredentials(credentialsDto);
    }

}
