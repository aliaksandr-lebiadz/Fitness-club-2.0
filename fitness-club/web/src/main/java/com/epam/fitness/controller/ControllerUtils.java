package com.epam.fitness.controller;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ControllerUtils {

    private static final String REDIRECT = "redirect:";
    private static final String FORWARD = "forward:";

    private UserService userService;

    @Autowired
    public ControllerUtils(UserService userService) {
        this.userService = userService;
    }

    public static String createRedirect(String page){
        return REDIRECT + page;
    }

    public static String createForward(String page){
        return FORWARD + page;
    }

    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if(!(principal instanceof UserDetails)){
            return Optional.empty();
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        return userService.findUserByEmail(email);
    }

}
