package com.epam.fitness.controller;

import com.epam.fitness.entity.GymMembershipDto;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.exception.UserNotFoundException;
import com.epam.fitness.service.api.GymMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class SimplePagesController{

    private static final String GET_MEMBERSHIP_PAGE = "get_membership";
    private static final String HOME_PAGE = "home";
    private static final String LOGIN_PAGE = "login";
    private static final String SIGN_UP_PAGE = "sign_up";
    private static final String ERROR_PAGE = "error_page";
    private static final String LOGIN_FAIL_PARAMETER = "login_fail";
    private static final String SIGN_UP_FAIL_PARAMETER = "sign_up_fail";
    private static final String DISCOUNT_ATTRIBUTE = "discount";
    private static final String GYM_MEMBERSHIPS_ATTRIBUTE = "gymMembershipList";
    private static final String LOGIN_PAGE_URL = "/login";

    private GymMembershipService gymMembershipService;
    private ControllerUtils utils;

    @Autowired
    public SimplePagesController(GymMembershipService gymMembershipService, ControllerUtils utils){
        this.gymMembershipService = gymMembershipService;
        this.utils = utils;
    }

    @GetMapping("/")
    public String getIndexPage(){
        Optional<UserDto> userOptional = utils.getCurrentUser();
        if(userOptional.isPresent()){
            return HOME_PAGE;
        } else{
            return ControllerUtils.createRedirect(LOGIN_PAGE_URL);
        }
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public String getLoginPage(@RequestParam(LOGIN_FAIL_PARAMETER) Optional<String> optionalFail, Model model){
        if(optionalFail.isPresent()){
            String fail = optionalFail.get();
            model.addAttribute(LOGIN_FAIL_PARAMETER, fail);
        }
        return LOGIN_PAGE;
    }

    @GetMapping("/sign-up")
    @PreAuthorize("isAnonymous()")
    public String getSignUpPage(@RequestParam(SIGN_UP_FAIL_PARAMETER) Optional<String> optionalFail, Model model) {
        if(optionalFail.isPresent()){
            String fail = optionalFail.get();
            model.addAttribute(SIGN_UP_FAIL_PARAMETER, fail);
        }
        return SIGN_UP_PAGE;
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public String getHomePage(){
        return HOME_PAGE;
    }

    @GetMapping("/order")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String getOrderPage(Model model) throws UserNotFoundException {
        Optional<UserDto> clientOptional = utils.getCurrentUser();
        UserDto client = clientOptional.orElseThrow(UserNotFoundException::new);

        int clientDiscount = client.getDiscount();
        model.addAttribute(DISCOUNT_ATTRIBUTE, clientDiscount);
        List<GymMembershipDto> gymMemberships = gymMembershipService.getAll();
        model.addAttribute(GYM_MEMBERSHIPS_ATTRIBUTE, gymMemberships);
        return GET_MEMBERSHIP_PAGE;
    }

    @GetMapping("/error")
    public String getErrorPage(){
        return ERROR_PAGE;
    }

}
