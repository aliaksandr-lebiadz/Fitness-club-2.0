package com.epam.fitness.controller;

import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.exception.ServiceException;
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
    private static final String ERROR_PAGE = "error_page";
    private static final String ERROR_PAGE_404 = "404_error_page";
    private static final String LOGIN_FAIL_PARAMETER = "login_fail";
    private static final String DISCOUNT_ATTRIBUTE = "discount";

    private GymMembershipService gymMembershipService;
    private ControllerUtils utils;

    @Autowired
    public SimplePagesController(GymMembershipService gymMembershipService, ControllerUtils utils){
        this.gymMembershipService = gymMembershipService;
        this.utils = utils;
    }

    @GetMapping({"/", "/login"})
    public String getLoginPage(
            @RequestParam("login_fail") Optional<Boolean> optionalFail,
            Model model){
        if(optionalFail.isPresent()){
            boolean fail = optionalFail.get();
            model.addAttribute(LOGIN_FAIL_PARAMETER, fail);
        }
        return LOGIN_PAGE;
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public String getHomePage(){
        return HOME_PAGE;
    }

    @GetMapping("/order")
    @PreAuthorize("hasAuthority('CLIENT')")
    public String getOrderPage(Model model) throws ServiceException {
        User client = utils.getCurrentUser();
        model.addAttribute(DISCOUNT_ATTRIBUTE, client.getDiscount());
        List<GymMembership> gymMemberships = gymMembershipService.getAll();
        model.addAttribute(gymMemberships);
        return GET_MEMBERSHIP_PAGE;
    }

    @GetMapping("/error")
    public String getErrorPage(){
        return ERROR_PAGE;
    }

    @GetMapping("/error404")
    public String get404ErrorPage(){
        return ERROR_PAGE_404;
    }

}
