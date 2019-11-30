package com.epam.fitness.command.impl.assignment;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.ValidationException;
import com.epam.fitness.service.api.AssignmentService;
import com.epam.fitness.utils.CurrentPageGetter;
import com.epam.fitness.validator.api.AssignmentValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeAssignmentCommand implements Command {

    private static final String ORDER_ID_PARAMETER = "order_id";
    private static final String ASSIGNMENT_ID_PARAMETER = "assignment_id";
    private static final String EXERCISE_SELECT_PARAMETER = "exercise_select";
    private static final String WORKOUT_DATE_PARAMETER = "date";
    private static final String AMOUNT_OF_SETS_PARAMETER = "amount_of_sets";
    private static final String AMOUNT_OF_REPS_PARAMETER = "amount_of_reps";
    private static final String OPERATION_PARAMETER = "operation";
    private static final String ADD_OPERATION = "add";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private AssignmentService service;
    private AssignmentValidator validator;

    public ChangeAssignmentCommand(AssignmentService service, AssignmentValidator validator){
        this.service = service;
        this.validator = validator;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException, ValidationException {
        try{
            String amountOfSetsStr = request.getParameter(AMOUNT_OF_SETS_PARAMETER);
            int amountOfSets = Integer.parseInt(amountOfSetsStr);
            String amountOfRepsStr = request.getParameter(AMOUNT_OF_REPS_PARAMETER);
            int amountOfReps = Integer.parseInt(amountOfRepsStr);
            String workoutDateStr = request.getParameter(WORKOUT_DATE_PARAMETER);
            Date workoutDate = DATE_FORMAT.parse(workoutDateStr);

            checkAssignmentParameters(amountOfSets, amountOfReps, workoutDate);

            String exerciseIdStr = request.getParameter(EXERCISE_SELECT_PARAMETER);
            int exerciseId = Integer.parseInt(exerciseIdStr);
            Exercise exercise = new Exercise(exerciseId);
            String operation = request.getParameter(OPERATION_PARAMETER);
            if(ADD_OPERATION.equals(operation)){
                String orderIdStr = request.getParameter(ORDER_ID_PARAMETER);
                int orderId = Integer.parseInt(orderIdStr);
                Assignment assignment = new Assignment(orderId, exercise, amountOfSets, amountOfReps, workoutDate);
                service.create(assignment);
            } else{ // change operation
                String assignmentIdStr = request.getParameter(ASSIGNMENT_ID_PARAMETER);
                int assignmentId = Integer.parseInt(assignmentIdStr);
                service.updateById(assignmentId, exercise, amountOfSets, amountOfReps, workoutDate);
            }
            String currentPage = CurrentPageGetter.getCurrentPage(request);
            return CommandResult.redirect(currentPage);
        } catch (ParseException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }

    }

    private void checkAssignmentParameters(int amountOfSets, int amountOfReps, Date workoutDate)
            throws ValidationException{
        if(!validator.isAmountOfSetsValid(amountOfSets)
                || !validator.isAmountOfRepsValid(amountOfReps)
                || !validator.isWorkoutDateValid(workoutDate)){
            throw new ValidationException("Assignment validation failed!");
        }
    }

}