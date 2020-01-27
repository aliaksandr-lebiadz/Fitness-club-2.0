package com.epam.fitness.controller;

import com.epam.fitness.entity.AssignmentDto;
import com.epam.fitness.entity.ExerciseDto;
import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.AssignmentService;
import com.epam.fitness.service.api.ExerciseService;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.utils.CurrentPageGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    private static final String ASSIGNMENTS_PAGE = "assignments";
    private static final String NUTRITION_TYPE_ATTRIBUTE = "nutrition_type";
    private static final String ACCEPT_ACTION = "accept";
    private static final String CANCEL_ACTION = "cancel";
    private static final String ORDERS_PAGE_URL = "/order/list";
    private static final String ASSIGNMENTS_ATTRIBUTE = "assignmentList";
    private static final String EXERCISES_ATTRIBUTE = "exerciseList";

    private AssignmentService assignmentService;
    private ExerciseService exerciseService;
    private OrderService orderService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService,
                                ExerciseService exerciseService,
                                OrderService orderService){
        this.assignmentService = assignmentService;
        this.exerciseService = exerciseService;
        this.orderService = orderService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('TRAINER')")
    public String getAssignmentsPage(
            @RequestParam("order_id") Optional<Integer> optionalOrderId,
            Model model) throws ServiceException{
        if(optionalOrderId.isPresent()){
            int orderId = optionalOrderId.get();
            List<AssignmentDto> assignments = assignmentService.getAllByOrderId(orderId);
            model.addAttribute(ASSIGNMENTS_ATTRIBUTE, assignments);
            NutritionType nutritionType = getNutritionTypeByOrderId(orderId);
            model.addAttribute(NUTRITION_TYPE_ATTRIBUTE, nutritionType);
            List<ExerciseDto> exercises = exerciseService.getAll();
            model.addAttribute(EXERCISES_ATTRIBUTE, exercises);
        } else{
            return ControllerUtils.createRedirect(ORDERS_PAGE_URL);
        }
        return ASSIGNMENTS_PAGE;
    }

    @PostMapping("/setStatus")
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('TRAINER')")
    public String setStatus(@RequestParam("assignment_id") int assignmentId,
                            @RequestParam("assignment_action") String assignmentAction,
                            HttpServletRequest request)
            throws ServiceException {
        AssignmentStatus status = getStatus(assignmentAction);
        assignmentService.updateStatusById(assignmentId, status);
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

    private NutritionType getNutritionTypeByOrderId(int orderId) throws ServiceException{
        Optional<OrderDto> orderOptional = orderService.getById(orderId);
        OrderDto orderDto = orderOptional
                .orElseThrow(() -> new ServiceException("Order with id " + orderId + " not found!"));
        return orderDto.getNutritionType();
    }

}
