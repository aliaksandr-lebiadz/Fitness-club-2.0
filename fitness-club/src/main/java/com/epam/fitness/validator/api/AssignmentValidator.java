package com.epam.fitness.validator.api;

import java.util.Date;

public interface AssignmentValidator {

    boolean isAmountOfRepsValid(int amountOfReps);
    boolean isAmountOfSetsValid(int amountOfSets);
    boolean isWorkoutDateValid(Date workoutDate);

}
