package com.epam.fitness.entity.assignment;

import com.epam.fitness.entity.Identifiable;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Assignment implements Identifiable, Serializable {

    private static final long serialVersionUID = -3238821726259450444L;

    private Integer id;
    private int orderId;
    private Date workoutDate;
    private int amountOfSets;
    private int amountOfReps;
    private Exercise exercise;
    private AssignmentStatus status;

    public Assignment(Exercise exercise, int amountOfSets, int amountOfReps,
                       Date workoutDate, AssignmentStatus status){
        this.exercise = exercise;
        this.amountOfSets = amountOfSets;
        this.amountOfReps = amountOfReps;
        this.workoutDate = workoutDate;
        this.status = status;
    }

    public Assignment(int orderId, Exercise exercise, int amountOfSets, int amountOfReps, Date workoutDate){
        this(exercise, amountOfSets, amountOfReps, workoutDate, AssignmentStatus.NEW);
        this.orderId = orderId;
    }

    public Assignment(Integer id, int orderId, Exercise exercise, int amountOfSets,
                      int amountOfReps, Date workoutDate, AssignmentStatus status){
        this(orderId, exercise, amountOfSets, amountOfReps, workoutDate);
        this.id = id;
        this.status = status;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public Date getWorkoutDate() {
        return workoutDate;
    }

    public int getAmountOfSets() {
        return amountOfSets;
    }

    public int getAmountOfReps() {
        return amountOfReps;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status){
        this.status = status;
    }

    public void setWorkoutDate(Date workoutDate) {
        this.workoutDate = workoutDate;
    }

    public void setAmountOfSets(int amountOfSets) {
        this.amountOfSets = amountOfSets;
    }

    public void setAmountOfReps(int amountOfReps) {
        this.amountOfReps = amountOfReps;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return orderId == that.orderId &&
                amountOfSets == that.amountOfSets &&
                amountOfReps == that.amountOfReps &&
                Objects.equals(id, that.id) &&
                workoutDate.equals(that.workoutDate) &&
                exercise.equals(that.exercise) &&
                status == that.status;
    }
}