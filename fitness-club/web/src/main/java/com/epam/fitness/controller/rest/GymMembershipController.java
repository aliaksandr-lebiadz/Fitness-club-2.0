package com.epam.fitness.controller.rest;

import com.epam.fitness.entity.GymMembershipDto;
import com.epam.fitness.service.api.GymMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gym-memberships")
public class GymMembershipController {

    private GymMembershipService gymMembershipService;

    @Autowired
    public GymMembershipController(GymMembershipService gymMembershipService){
        this.gymMembershipService = gymMembershipService;
    }

    @GetMapping
    public List<GymMembershipDto> getGymMemberships(){
        return gymMembershipService.getAll();
    }

}
