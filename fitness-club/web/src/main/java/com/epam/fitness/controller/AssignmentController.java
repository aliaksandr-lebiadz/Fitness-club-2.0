package com.epam.fitness.controller;

import com.epam.fitness.entity.AssignmentDto;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    private AssignmentService assignmentService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService){
        this.assignmentService = assignmentService;
    }

    @PutMapping("/{id}")
    public void updateAssignment(@PathVariable int id, @Valid @RequestBody AssignmentDto assignmentDto) throws ServiceException {
        assignmentService.updateById(id, assignmentDto);
    }

}
