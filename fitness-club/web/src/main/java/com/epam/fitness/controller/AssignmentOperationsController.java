package com.epam.fitness.controller;

import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.ValidationException;
import com.epam.fitness.service.api.AssignmentService;
import com.epam.fitness.utils.CurrentPageGetter;
import com.epam.fitness.validator.api.AssignmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/assignmentOperations")
public class AssignmentOperationsController {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private AssignmentService service;
    private AssignmentValidator validator;

    @Autowired
    public AssignmentOperationsController(AssignmentService service, AssignmentValidator validator){
        this.service = service;
        this.validator = validator;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('TRAINER')")
    public String add(@RequestParam("amount_of_sets") int amountOfSets,
                      @RequestParam("amount_of_reps") int amountOfReps,
                      @RequestParam("date")
                          @DateTimeFormat(pattern = DATE_PATTERN) Date workoutDate,
                      @RequestParam("exercise_select") int exerciseId,
                      @RequestParam("order_id") int orderId,
                      HttpServletRequest request) throws ServiceException, ValidationException {
        validateWorkoutDate(workoutDate);
        service.create(orderId, exerciseId, amountOfSets, amountOfReps, workoutDate);
        String currentPage = CurrentPageGetter.getCurrentPage(request);
        return ControllerUtils.createRedirect(currentPage);
    }

    @PostMapping("/change")
    @PreAuthorize("hasAuthority('TRAINER') or hasAuthority('CLIENT')")
    public String change(@RequestParam("amount_of_sets") int amountOfSets,
                         @RequestParam("amount_of_reps") int amountOfReps,
                         @RequestParam("date")
                             @DateTimeFormat(pattern = DATE_PATTERN) Date workoutDate,
                         @RequestParam("exercise_select") int exerciseId,
                         @RequestParam("assignment_id") int assignmentId,
                         HttpServletRequest request) throws ServiceException, ValidationException {
        validateWorkoutDate(workoutDate);
        Exercise exercise = new Exercise(exerciseId);
        service.updateById(assignmentId, exercise, amountOfSets, amountOfReps, workoutDate);
        String currentPage = CurrentPageGetter.getCurrentPage(request);
        return ControllerUtils.createRedirect(currentPage);
    }

    private void validateWorkoutDate(Date workoutDate) throws ValidationException{
        if(!validator.isWorkoutDateValid(workoutDate)){
            throw new ValidationException("Invalid workout date!");
        }
    }

}
