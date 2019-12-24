package com.epam.fitness.filter.helper;

import com.epam.fitness.entity.user.User;

import static com.epam.fitness.command.Commands.*;

public class CommandAccessController {

    public boolean hasAccess(String command, User user){
        if(command == null){
            return false;
        }
        switch (command){
            case SHOW_CLIENTS_COMMAND:
            case SET_USER_DISCOUNT_COMMAND:
                return UserAccessUtils.isAdmin(user);

            case ASSIGN_NUTRITION_TYPE_COMMAND:
            case SHOW_TRAINER_CLIENTS_COMMAND:
                return UserAccessUtils.isTrainer(user);

            case GET_MEMBERSHIP_COMMAND:
            case SHOW_ORDER_PAGE_COMMAND:
            case SHOW_ORDERS_COMMAND:
            case SEND_FEEDBACK_COMMAND:
                return UserAccessUtils.isClient(user);

            case SHOW_ASSIGNMENTS_COMMAND:
            case CHANGE_ASSIGNMENT_COMMAND:
            case CHANGE_ASSIGNMENT_STATUS_COMMAND:
                return UserAccessUtils.isClient(user) || UserAccessUtils.isTrainer(user);

            default:
                return true;
        }
    }

}
