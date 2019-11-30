package com.epam.fitness.command.factory.impl;

import com.epam.fitness.command.Command;
import com.epam.fitness.command.factory.CommandFactory;
import com.epam.fitness.command.impl.*;
import com.epam.fitness.command.impl.assignment.ChangeAssignmentCommand;
import com.epam.fitness.command.impl.order.SendFeedbackCommand;
import com.epam.fitness.command.impl.order.ShowOrdersCommand;
import com.epam.fitness.command.impl.user.LogOutCommand;
import com.epam.fitness.command.impl.user.LoginCommand;
import com.epam.fitness.dao.factory.DaoFactory;
import com.epam.fitness.service.api.OrderService;
import com.epam.fitness.service.impl.*;
import com.epam.fitness.command.impl.assignment.ChangeAssignmentStatusCommand;
import com.epam.fitness.command.impl.assignment.ShowAssignmentsCommand;
import com.epam.fitness.command.impl.order.AssignNutritionTypeCommand;
import com.epam.fitness.command.impl.user.SetUserDiscountCommand;
import com.epam.fitness.utils.OrderUtils;
import com.epam.fitness.validator.impl.AssignmentValidatorImpl;
import com.epam.fitness.validator.impl.OrderValidatorImpl;
import com.epam.fitness.validator.impl.PaymentValidatorImpl;
import com.epam.fitness.validator.impl.UserValidatorImpl;

public class CommandFactoryImpl implements CommandFactory {

    private static final String LOGIN_COMMAND = "login";
    private static final String LOG_OUT_COMMAND = "logOut";
    private static final String SET_LOCALE_COMMAND = "setLocale";
    private static final String SHOW_HOME_PAGE_COMMAND = "showHomePage";
    
    public static final String SHOW_ORDER_PAGE_COMMAND = "showOrderPage";
    public static final String GET_MEMBERSHIP_COMMAND = "getMembership";
    public static final String SHOW_ORDERS_COMMAND = "showOrders";
    public static final String SEND_FEEDBACK_COMMAND = "sendFeedback";
    public static final String SHOW_TRAINER_CLIENTS_COMMAND = "showTrainerClients";
    public static final String ASSIGN_NUTRITION_TYPE_COMMAND = "assignNutritionType";
    public static final String SHOW_ASSIGNMENTS_COMMAND = "showAssignments";
    public static final String CHANGE_ASSIGNMENT_STATUS_COMMAND = "changeAssignmentStatus";
    public static final String CHANGE_ASSIGNMENT_COMMAND = "changeAssignment";
    public static final String SHOW_CLIENTS_COMMAND = "showClients";
    public static final String SET_USER_DISCOUNT_COMMAND = "setUserDiscount";

    private DaoFactory factory;

    public CommandFactoryImpl(DaoFactory factory){
        this.factory = factory;
    }


    public Command create(String commandValue){
        Command command;
        switch (commandValue){
            case LOGIN_COMMAND:
                command =  new LoginCommand(
                        new UserServiceImpl(factory)
                );
                break;
            case SHOW_ORDER_PAGE_COMMAND:
                command = new ShowOrderPageCommand(
                        new GymMembershipServiceImpl(factory)
                );
                break;
            case GET_MEMBERSHIP_COMMAND:
                command =  new GetMembershipCommand(
                        getOrderService(),
                        new PaymentValidatorImpl()
                );
                break;
            case SHOW_ORDERS_COMMAND:
                command =  new ShowOrdersCommand(
                       getOrderService()
                );
                break;
            case SEND_FEEDBACK_COMMAND:
                command =  new SendFeedbackCommand(
                        getOrderService(),
                        new OrderValidatorImpl()
                );
                break;
            case SHOW_TRAINER_CLIENTS_COMMAND:
                command = new ShowTrainerClientsCommand(
                        new UserServiceImpl(factory),
                        getOrderService(),
                        new ExerciseServiceImpl(factory)
                );
                break;
            case ASSIGN_NUTRITION_TYPE_COMMAND:
                command = new AssignNutritionTypeCommand(
                        getOrderService()
                );
                break;
            case SHOW_ASSIGNMENTS_COMMAND:
                command = new ShowAssignmentsCommand(
                        new AssignmentServiceImpl(factory),
                        new ExerciseServiceImpl(factory)
                );
                break;
            case CHANGE_ASSIGNMENT_STATUS_COMMAND:
                command = new ChangeAssignmentStatusCommand(
                        new AssignmentServiceImpl(factory)
                );
                break;
            case CHANGE_ASSIGNMENT_COMMAND:
                command = new ChangeAssignmentCommand(
                        new AssignmentServiceImpl(factory),
                        new AssignmentValidatorImpl()
                );
                break;
            case SHOW_CLIENTS_COMMAND:
                command = new ShowClientsCommand(
                        new UserServiceImpl(factory)
                );
                break;
            case SET_USER_DISCOUNT_COMMAND:
                command = new SetUserDiscountCommand(
                        new UserServiceImpl(factory),
                        new UserValidatorImpl()
                );
                break;
            case SHOW_HOME_PAGE_COMMAND:
                command = new ShowHomePageCommand();
                break;
            case LOG_OUT_COMMAND:
                command =  new LogOutCommand();
                break;
            case SET_LOCALE_COMMAND:
                command = new SetLocaleCommand();
                break;
            default:
                throw new IllegalArgumentException("Illegal command: " + commandValue);
        }
        return command;
    }

    private OrderService getOrderService(){
        OrderUtils orderUtils = new OrderUtils();
        return new OrderServiceImpl(factory, orderUtils);
    }

}
