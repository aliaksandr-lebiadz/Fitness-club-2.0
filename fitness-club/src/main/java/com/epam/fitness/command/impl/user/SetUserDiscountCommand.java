package com.epam.fitness.command.impl.user;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.CommandResult;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.exception.ValidationException;
import com.epam.fitness.service.api.UserService;
import com.epam.fitness.utils.CurrentPageGetter;
import com.epam.fitness.validator.api.UserValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetUserDiscountCommand implements Command {

    private static final String USER_ID_PARAMETER = "user_id";
    private static final String DISCOUNT_PARAMETER = "discount";

    private UserService service;
    private UserValidator validator;

    public SetUserDiscountCommand(UserService service, UserValidator validator){
        this.service = service;
        this.validator = validator;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException, ValidationException {
        String discountStr = request.getParameter(DISCOUNT_PARAMETER);
        int discount = Integer.parseInt(discountStr);
        if(!validator.isDiscountValid(discount)){
            throw new ValidationException("Discount validation failed!");
        }
        String userIdStr = request.getParameter(USER_ID_PARAMETER);
        int userId = Integer.parseInt(userIdStr);
        service.setUserDiscount(userId, discount);
        String currentPage = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.redirect(currentPage);
    }
}