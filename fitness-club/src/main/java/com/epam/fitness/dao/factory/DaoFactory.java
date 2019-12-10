package com.epam.fitness.dao.factory;

import com.epam.fitness.dao.api.*;

/**
 * <p>A factory interface for creation of the dao classes.</p>
 */
public interface DaoFactory {

    UserDao createUserDao();
    OrderDao createOrderDao();
    GymMembershipDao createGymMembershipDao();
    ExerciseDao createExerciseDao();
    AssignmentDao createAssignmentDao();
}