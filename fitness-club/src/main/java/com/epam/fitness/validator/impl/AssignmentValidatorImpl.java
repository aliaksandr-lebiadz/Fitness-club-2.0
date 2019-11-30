package com.epam.fitness.validator.impl;

import com.epam.fitness.validator.api.AssignmentValidator;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class AssignmentValidatorImpl implements AssignmentValidator {

    private static final int MIN_AMOUNT_OF_REPS = 1;
    private static final int MIN_AMOUNT_OF_SETS = 1;
    private static final int MAX_AMOUNT_OF_REPS = 100;
    private static final int MAX_AMOUNT_OF_SETS = 100;

    @Override
    public boolean isAmountOfRepsValid(int amount){
        return amount >= MIN_AMOUNT_OF_REPS && amount <= MAX_AMOUNT_OF_REPS;
    }

    @Override
    public boolean isAmountOfSetsValid(int amount){
        return amount >= MIN_AMOUNT_OF_SETS && amount <= MAX_AMOUNT_OF_SETS;
    }

    @Override
    public boolean isWorkoutDateValid(Date workoutDate){
        Date now = new Date();
        Date today = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);
        return !workoutDate.before(today);
    }

}