package com.epam.fitness.controller;

import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.ValidationException;
import com.epam.fitness.service.api.AssignmentService;
import com.epam.fitness.utils.CurrentPageGetter;
import com.epam.fitness.validator.api.AssignmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/assignmentOperations")
public class AssignmentOperationsController {

    private static final String ORDER_ID_PARAMETER = "order_id";
    private static final String ASSIGNMENT_ID_PARAMETER = "assignment_id";
    private static final String EXERCISE_SELECT_PARAMETER = "exercise_select";
    private static final String WORKOUT_DATE_PARAMETER = "date";
    private static final String AMOUNT_OF_SETS_PARAMETER = "amount_of_sets";
    private static final String AMOUNT_OF_REPS_PARAMETER = "amount_of_reps";
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private AssignmentService service;
    private AssignmentValidator validator;

    @Autowired
    public AssignmentOperationsController(AssignmentService service, AssignmentValidator validator){
        this.service = service;
        this.validator = validator;
    }

    @RequestMapping("/add")
    public String add(@RequestParam(AMOUNT_OF_SETS_PARAMETER) int amountOfSets,
                      @RequestParam(AMOUNT_OF_REPS_PARAMETER) int amountOfReps,
                      @RequestParam(WORKOUT_DATE_PARAMETER)
                      @DateTimeFormat(pattern = DATE_PATTERN) Date workoutDate,
                      @RequestParam(EXERCISE_SELECT_PARAMETER) int exerciseId,
                      @RequestParam(ORDER_ID_PARAMETER) int orderId,
                      HttpServletRequest request)
            throws ServiceException, ValidationException {
        validateAssignmentParameters(amountOfSets, amountOfReps, workoutDate);
        Exercise exercise = new Exercise(exerciseId);
        Assignment assignment = new Assignment(orderId, exercise, amountOfSets, amountOfReps, workoutDate);
        service.create(assignment);
        String currentPage = CurrentPageGetter.getCurrentPage(request);
        return ControllerUtils.createRedirect(currentPage);
    }

    @RequestMapping("/change")
    public String change(@RequestParam(AMOUNT_OF_SETS_PARAMETER) int amountOfSets,
                         @RequestParam(AMOUNT_OF_REPS_PARAMETER) int amountOfReps,
                         @RequestParam(WORKOUT_DATE_PARAMETER)
                         @DateTimeFormat(pattern = DATE_PATTERN) Date workoutDate,
                         @RequestParam(EXERCISE_SELECT_PARAMETER) int exerciseId,
                         @RequestParam(ASSIGNMENT_ID_PARAMETER) int assignmentId,
                         HttpServletRequest request)
            throws ServiceException, ValidationException {
        validateAssignmentParameters(amountOfSets, amountOfReps, workoutDate);
        Exercise exercise = new Exercise(exerciseId);
        service.updateById(assignmentId, exercise, amountOfSets, amountOfReps, workoutDate);
        String currentPage = CurrentPageGetter.getCurrentPage(request);
        return ControllerUtils.createRedirect(currentPage);
    }

    private void validateAssignmentParameters(int amountOfSets, int amountOfReps, Date workoutDate)
            throws ValidationException{
        if(!validator.isAmountOfSetsValid(amountOfSets)
                || !validator.isAmountOfRepsValid(amountOfReps)
                || !validator.isWorkoutDateValid(workoutDate)){
            throw new ValidationException("Assignment validation failed!");
        }
    }

}
