package com.epam.fitness.command.impl.order;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;
import com.epam.fitness.entity.order.NutritionType;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.utils.CurrentPageGetter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AssignNutritionTypeCommand implements Command {

    private static final String NUTRITION_TYPE_PARAMETER = "nutrition_type";
    private static final String ORDER_ID_PARAMETER = "order_id";

    private OrderService service;

    public AssignNutritionTypeCommand(OrderService service){
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String nutritionTypeValue = request.getParameter(NUTRITION_TYPE_PARAMETER);
        NutritionType nutritionType = NutritionType.getNutritionType(nutritionTypeValue);
        String orderIdStr = request.getParameter(ORDER_ID_PARAMETER);
        int orderId = Integer.parseInt(orderIdStr);
        service.updateNutritionById(orderId, nutritionType);
        String currentPage = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.redirect(currentPage);
    }
}