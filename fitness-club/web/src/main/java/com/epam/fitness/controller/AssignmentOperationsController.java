package com.epam.fitness.controller;

import com.epam.fitness.entity.AssignmentDto;
import com.epam.fitness.entity.ExerciseDto;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.AssignmentService;
import com.epam.fitness.utils.CurrentPageGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Controller
@RequestMapping("/assignmentOperations")
public class AssignmentOperationsController {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private AssignmentService service;

    @Autowired
    public AssignmentOperationsController(AssignmentService service){
        this.service = service;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('TRAINER')")
    public String add(@RequestParam("amount_of_sets") @Min(1) @Max(100) int amountOfSets,
                      @RequestParam("amount_of_reps") @Min(1) @Max(100) int amountOfReps,
                      @RequestParam("date")
                          @DateTimeFormat(pattern = DATE_PATTERN) @FutureOrPresent Date workoutDate,
                      @RequestParam("exercise_select") int exerciseId,
                      @RequestParam("order_id") int orderId,
                      HttpServletRequest request) throws ServiceException {
        AssignmentDto assignmentDto = new AssignmentDto(amountOfSets, amountOfReps, workoutDate);
        service.create(orderId, exerciseId, assignmentDto);
        String currentPage = CurrentPageGetter.getCurrentPage(request);
        return ControllerUtils.createRedirect(currentPage);
    }

    @PostMapping("/change")
    @PreAuthorize("hasAuthority('TRAINER') or hasAuthority('CLIENT')")
    public String change(@RequestParam("amount_of_sets") @Min(1) @Max(100) int amountOfSets,
                         @RequestParam("amount_of_reps") @Min(1) @Max(100) int amountOfReps,
                         @RequestParam("date")
                             @DateTimeFormat(pattern = DATE_PATTERN) @FutureOrPresent Date workoutDate,
                         @RequestParam("exercise_select") int exerciseId,
                         @RequestParam("assignment_id") int assignmentId,
                         HttpServletRequest request) throws ServiceException {
        ExerciseDto exerciseDto = new ExerciseDto(exerciseId);
        AssignmentDto assignmentDto =
                new AssignmentDto(assignmentId, exerciseDto, amountOfSets, amountOfReps, workoutDate);
        service.update(assignmentDto);
        String currentPage = CurrentPageGetter.getCurrentPage(request);
        return ControllerUtils.createRedirect(currentPage);
    }
}
