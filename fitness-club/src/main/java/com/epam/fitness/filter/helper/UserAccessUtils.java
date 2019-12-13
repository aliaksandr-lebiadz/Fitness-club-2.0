package com.epam.fitness.filter.helper;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;

public class UserAccessUtils {

    private UserAccessUtils() {}

    public static boolean isAdmin(User user){
        return isNotNull(user) && (user.getRole() == UserRole.ADMIN);
    }

    public static boolean isClient(User user){
        return isNotNull(user) && (user.getRole() == UserRole.CLIENT);
    }

    public static boolean isTrainer(User user){
        return isNotNull(user) && (user.getRole() == UserRole.TRAINER);
    }

    private static boolean isNotNull(User user){
        return user != null;
    }

}
