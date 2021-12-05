package com.epam.fitness.entity;

import com.epam.fitness.entity.assignment.AssignmentStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class AssignmentDto implements Identifiable, Serializable {

    private static final long serialVersionUID = -2057066388628609370L;

    private Integer id;
    private Date workoutDate;
    private int amountOfSets;
    private int amountOfReps;
    private ExerciseDto exercise;
    private AssignmentStatus status;

    public AssignmentDto() {}

    public AssignmentDto(int id, AssignmentStatus status){
        this.id = id;
        this.status = status;
    }

    public AssignmentDto(int amountOfSets, int amountOfReps, Date workoutDate){
        this.amountOfSets = amountOfSets;
        this.amountOfReps = amountOfReps;
        this.workoutDate = workoutDate;
    }

    public AssignmentDto(int id, ExerciseDto exercise, int amountOfSets, int amountOfReps, Date workoutDate){
        this(amountOfSets, amountOfReps, workoutDate);
        this.id = id;
        this.exercise = exercise;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(Date workoutDate) {
        this.workoutDate = workoutDate;
    }

    public int getAmountOfSets() {
        return amountOfSets;
    }

    public void setAmountOfSets(int amountOfSets) {
        this.amountOfSets = amountOfSets;
    }

    public int getAmountOfReps() {
        return amountOfReps;
    }

    public void setAmountOfReps(int amountOfReps) {
        this.amountOfReps = amountOfReps;
    }

    public ExerciseDto getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseDto exercise) {
        this.exercise = exercise;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentDto that = (AssignmentDto) o;
        return amountOfSets == that.amountOfSets &&
                amountOfReps == that.amountOfReps &&
                Objects.equals(id, that.id) &&
                Objects.equals(workoutDate, that.workoutDate) &&
                Objects.equals(exercise, that.exercise) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workoutDate, amountOfSets, amountOfReps, exercise, status);
    }
}
