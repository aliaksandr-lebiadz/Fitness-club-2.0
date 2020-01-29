package com.epam.fitness.entity;

import com.epam.fitness.entity.assignment.AssignmentStatus;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;

public class AssignmentDto implements Identifiable, Serializable {

    private static final long serialVersionUID = -2057066388628609370L;

    private Integer id;
    @FutureOrPresent
    private LocalDate workoutDate;
    @Min(1)
    private int amountOfSets;
    @Min(1)
    private int amountOfReps;
    private ExerciseDto exercise;
    private AssignmentStatus status;

    public AssignmentDto() {
        this.status = AssignmentStatus.NEW;
    }

    public AssignmentDto(int id, AssignmentStatus status){
        this.id = id;
        this.status = status;
    }

    public AssignmentDto(int amountOfSets, int amountOfReps, LocalDate workoutDate){
        this.amountOfSets = amountOfSets;
        this.amountOfReps = amountOfReps;
        this.workoutDate = workoutDate;
    }

    public AssignmentDto(int id, ExerciseDto exercise, int amountOfSets, int amountOfReps, LocalDate workoutDate){
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

    public LocalDate getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(LocalDate workoutDate) {
        this.workoutDate = workoutDate;
    }

    public Integer getAmountOfSets() {
        return amountOfSets;
    }

    public void setAmountOfSets(int amountOfSets) {
        this.amountOfSets = amountOfSets;
    }

    public Integer getAmountOfReps() {
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
}
