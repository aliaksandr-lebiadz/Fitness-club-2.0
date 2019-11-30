package com.epam.fitness.command.impl.assignment;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.AssignmentService;
import com.epam.fitness.service.api.ExerciseService;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.Exercise;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAssignmentsCommand implements Command {

    private static final String ASSIGNMENTS_PAGE_URL = "/assignments";
    private static final String ORDER_ID = "order_id";
    private static final String ASSIGNMENTS_ATTRIBUTE = "assignments";
    private static final String NUTRITION_TYPE_ATTRIBUTE = "nutrition_type";
    private static final String EXERCISES_ATTRIBUTE = "exercises";

    private AssignmentService assignmentService;
    private ExerciseService exerciseService;

    public ShowAssignmentsCommand(AssignmentService assignmentService, ExerciseService exerciseService){
        this.assignmentService = assignmentService;
        this.exerciseService = exerciseService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException {
        String orderIdStr = request.getParameter(ORDER_ID);
        int orderId = Integer.parseInt(orderIdStr);
        List<Assignment> assignments = assignmentService.getAllByOrderId(orderId);
        request.setAttribute(ASSIGNMENTS_ATTRIBUTE, assignments);
        NutritionType nutritionType = assignmentService.getNutritionTypeByOrderId(orderId);
        request.setAttribute(NUTRITION_TYPE_ATTRIBUTE, nutritionType);
        List<Exercise> exercises = exerciseService.getAll();
        request.setAttribute(EXERCISES_ATTRIBUTE, exercises);
        return CommandResult.forward(ASSIGNMENTS_PAGE_URL);
    }
}