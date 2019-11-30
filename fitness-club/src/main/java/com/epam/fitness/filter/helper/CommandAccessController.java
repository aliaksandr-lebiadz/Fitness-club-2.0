package com.epam.fitness.filter.helper;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;

import static com.epam.fitness.command.factory.impl.CommandFactoryImpl.*;

public class CommandAccessController {

    public boolean hasAccess(String command, User user){
        if(command == null){
            return false;
        }
        switch (command){
            case SHOW_CLIENTS_COMMAND:
            case SET_USER_DISCOUNT_COMMAND:
                return user != null && user.getRole() == UserRole.ADMIN;

            case ASSIGN_NUTRITION_TYPE_COMMAND:
            case SHOW_TRAINER_CLIENTS_COMMAND:
                return user != null && user.getRole() == UserRole.TRAINER;

            case GET_MEMBERSHIP_COMMAND:
            case SHOW_ORDER_PAGE_COMMAND:
            case SHOW_ORDERS_COMMAND:
            case SEND_FEEDBACK_COMMAND:
                return user != null && user.getRole() == UserRole.CLIENT;

            case SHOW_ASSIGNMENTS_COMMAND:
            case CHANGE_ASSIGNMENT_COMMAND:
            case CHANGE_ASSIGNMENT_STATUS_COMMAND:
                return user != null && (user.getRole() == UserRole.CLIENT || user.getRole() == UserRole.TRAINER);

            default:
                return true;
        }
    }

}
