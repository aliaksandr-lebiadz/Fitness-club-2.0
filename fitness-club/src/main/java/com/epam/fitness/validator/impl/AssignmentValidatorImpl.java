package com.epam.fitness.validator.impl;

import com.epam.fitness.utils.DateUtils;
import com.epam.fitness.validator.api.AssignmentValidator;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AssignmentValidatorImpl implements AssignmentValidator {

    private static final int MIN_AMOUNT_OF_REPS = 1;
    private static final int MIN_AMOUNT_OF_SETS = 1;
    private static final int MAX_AMOUNT_OF_REPS = 100;
    private static final int MAX_AMOUNT_OF_SETS = 100;

    private DateUtils dateUtils;

    public AssignmentValidatorImpl(DateUtils dateUtils){
        this.dateUtils = dateUtils;
    }

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
        return isNotBeforeToday(workoutDate);
    }

    private boolean isNotBeforeToday(Date date){
        Date today = dateUtils.getCurrentDateWithoutTime();
        return !date.before(today);
    }
}