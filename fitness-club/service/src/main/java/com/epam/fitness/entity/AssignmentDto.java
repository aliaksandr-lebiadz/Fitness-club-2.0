package com.epam.fitness.entity;

import com.epam.fitness.entity.assignment.AssignmentStatus;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class AssignmentDto implements Identifiable, Serializable {

    private static final long serialVersionUID = -2057066388628609370L;

    private Integer id;

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
        this();
        this.amountOfSets = amountOfSets;
        this.amountOfReps = amountOfReps;
        this.workoutDate = workoutDate;
    }

    public AssignmentDto(int id, int exerciseId, int amountOfSets, int amountOfReps){
        this();
        this.id = id;
        this.exerciseId = exerciseId;
        this.amountOfSets = amountOfSets;
        this.amountOfReps = amountOfReps;
    }

    public AssignmentDto(int id, int exerciseId, int amountOfSets, int amountOfReps, LocalDate workoutDate){
        this(id, exerciseId, amountOfSets, amountOfReps);
        this.workoutDate = workoutDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentDto that = (AssignmentDto) o;
        return amountOfSets == that.amountOfSets &&
                amountOfReps == that.amountOfReps &&
                exerciseId == that.exerciseId &&
                Objects.equals(id, that.id) &&
                Objects.equals(workoutDate, that.workoutDate) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workoutDate, amountOfSets, amountOfReps, exerciseId, status);
    }
}
