package com.epam.fitness.controller;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/account")
public class AccountController {

    private static final String HOME_PAGE_URL = "/home";
    private static final String LOGIN_PAGE_URL = "/login";
    private static final String USER_ATTRIBUTE = "user";
    private static final String LOGIN_FAIL_ATTRIBUTE = "login_fail";
    private static final String LOCALE_ATTRIBUTE = "locale";

    private UserService service;

    @Autowired
    public AccountController(UserService service){
        this.service = service;
    }

    @PostMapping("/logIn")
    public String logIn(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session){
        Optional<User> optionalUser = service.login(email, password);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            session.setAttribute(USER_ATTRIBUTE, user);
            return ControllerUtils.createRedirect(HOME_PAGE_URL);
        } else {
            session.setAttribute(LOGIN_FAIL_ATTRIBUTE, true);
            return ControllerUtils.createRedirect(LOGIN_PAGE_URL);
        }
    }

    @RequestMapping("/logOut")
    public String logOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(LOCALE_ATTRIBUTE);
        session.invalidate();
        setLocaleToSession(request, locale);
        return ControllerUtils.createRedirect(LOGIN_PAGE_URL);
    }

    private void setLocaleToSession(HttpServletRequest request, Locale locale){
        HttpSession session = request.getSession();
        session.setAttribute(LOCALE_ATTRIBUTE, locale);
    }

}
