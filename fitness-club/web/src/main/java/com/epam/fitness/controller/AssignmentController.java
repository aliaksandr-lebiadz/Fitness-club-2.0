package com.epam.fitness.controller;

import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.ValidationException;
import com.epam.fitness.service.api.AssignmentService;
import com.epam.fitness.service.api.ExerciseService;
import com.epam.fitness.utils.CurrentPageGetter;
import com.epam.fitness.validator.api.AssignmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    private static final String ASSIGNMENTS_PAGE = "assignments";
    private static final String ORDER_ID_PARAMETER = "order_id";
    private static final String NUTRITION_TYPE_ATTRIBUTE = "nutrition_type";
    private static final String ASSIGNMENT_ID_PARAMETER = "assignment_id";
    private static final String ASSIGNMENT_ACTION_PARAMETER = "assignment_action";
    private static final String ACCEPT_ACTION = "accept";
    private static final String CANCEL_ACTION = "cancel";

    private AssignmentService assignmentService;
    private ExerciseService exerciseService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService,
                                ExerciseService exerciseService){
        this.assignmentService = assignmentService;
        this.exerciseService = exerciseService;
    }

    @RequestMapping("/list")
    public String showAssignmentsPage(@RequestParam(ORDER_ID_PARAMETER) int orderId,
                                      Model model) throws ServiceException{

        List<Assignment> assignments = assignmentService.getAllByOrderId(orderId);
        model.addAttribute(assignments);
        NutritionType nutritionType = assignmentService.getNutritionTypeByOrderId(orderId);
        model.addAttribute(NUTRITION_TYPE_ATTRIBUTE, nutritionType);
        List<Exercise> exercises = exerciseService.getAll();
        model.addAttribute(exercises);
        return ASSIGNMENTS_PAGE;
    }

    @RequestMapping("/setStatus")
    public String setStatus(@RequestParam(ASSIGNMENT_ID_PARAMETER) int assignmentId,
                            @RequestParam(ASSIGNMENT_ACTION_PARAMETER) String assignmentAction,
                            HttpServletRequest request)
            throws ServiceException {
        AssignmentStatus status = getStatus(assignmentAction);
        assignmentService.changeStatusById(assignmentId, status);
        String currentPage = CurrentPageGetter.getCurrentPage(request);
        return ControllerUtils.createRedirect(currentPage);
    }

    private AssignmentStatus getStatus(String assignmentAction) throws ServiceException{
        AssignmentStatus status;
        switch (assignmentAction){
            case ACCEPT_ACTION:
                status = AssignmentStatus.ACCEPTED;
                break;
            case CANCEL_ACTION:
                status = AssignmentStatus.CANCELED;
                break;
            default:
                throw new ServiceException("Invalid assignment action: " + assignmentAction);
        }
        return status;
    }

}
