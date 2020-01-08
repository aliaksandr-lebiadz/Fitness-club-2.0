package com.epam.fitness.controller;

import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.service.api.GymMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SimplePagesController {

    private static final String GET_MEMBERSHIP_PAGE = "get_membership";
    private static final String HOME_PAGE = "home";
    private static final String LOGIN_PAGE = "login";
    private static final String ERROR_PAGE = "error_page";
    private static final String ERROR_PAGE_404 = "404_error_page";

    private GymMembershipService gymMembershipService;

    @Autowired
    public SimplePagesController(GymMembershipService gymMembershipService){
        this.gymMembershipService = gymMembershipService;
    }

    @GetMapping({"/", "/login"})
    public String getLoginPage(){
        return LOGIN_PAGE;
    }

    @GetMapping("/home")
    public String getHomePage(){
        return HOME_PAGE;
    }

    @GetMapping("/order")
    public String getOrderPage(Model model){
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
