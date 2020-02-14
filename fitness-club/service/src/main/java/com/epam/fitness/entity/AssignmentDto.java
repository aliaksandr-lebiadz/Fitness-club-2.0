package com.epam.fitness.entity;

import com.epam.fitness.entity.assignment.AssignmentStatus;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public class AssignmentDto implements Identifiable, Serializable {

    private static final long serialVersionUID = -2057066388628609370L;

    private Integer id;

    @NotNull
    @FutureOrPresent
    private LocalDate workoutDate;

    @NotNull
    @Min(1)
    private int amountOfSets;

    @NotNull
    @Min(1)
    private int amountOfReps;

    private int exerciseId;
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

    public AssignmentDto(int id, int exerciseId, int amountOfSets, int amountOfReps, LocalDate workoutDate){
        this(amountOfSets, amountOfReps, workoutDate);
        this.id = id;
        this.exerciseId = exerciseId;
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

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }
}
