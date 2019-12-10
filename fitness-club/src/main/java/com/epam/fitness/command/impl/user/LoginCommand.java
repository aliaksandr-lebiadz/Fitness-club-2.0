package com.epam.fitness.command.impl.user;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {

    private static final String HOME_PAGE_URL = "/home";
    private static final String LOGIN_PAGE_URL = "/login";
    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String USER_ATTRIBUTE = "user";
    private static final String LOGIN_FAIL_ATTRIBUTE = "login_fail";

    private UserService service;

    public LoginCommand(UserService service){
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException{
        String email = request.getParameter(EMAIL_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        Optional<User> optionalUser = service.login(email, password);
        HttpSession session = request.getSession();
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            session.setAttribute(USER_ATTRIBUTE, user);
            return CommandResult.redirect(HOME_PAGE_URL);
        } else {
            session.setAttribute(LOGIN_FAIL_ATTRIBUTE, true);
            return CommandResult.redirect(LOGIN_PAGE_URL);
        }
    }
}