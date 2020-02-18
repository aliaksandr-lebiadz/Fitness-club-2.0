package com.epam.fitness.entity.assignment;

import com.epam.fitness.entity.Identifiable;
import com.epam.fitness.entity.order.Order;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "assignment")
public class Assignment implements Identifiable, Serializable {

    private static final long serialVersionUID = -3238821726259450444L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    private Order order;

    @Column(name = "workout_date")
    @NotNull
    private LocalDate workoutDate;

    @Column(name = "amount_of_sets")
    @Min(1)
    private int amountOfSets;

    @Column(name = "amount_of_reps")
    @Min(1)
    private int amountOfReps;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AssignmentStatus status;

    public Assignment() {}

    public Assignment(int id, LocalDate workoutDate, int amountOfSets, int amountOfReps, Exercise exercise, AssignmentStatus status){
        this.id = id;
        this.workoutDate = workoutDate;
        this.amountOfSets = amountOfSets;
        this.amountOfReps = amountOfReps;
        this.status = status;
        this.exercise = exercise;
    }

    public Assignment(Integer id, Order order, Exercise exercise, int amountOfSets,
                      int amountOfReps, LocalDate workoutDate, AssignmentStatus status){
        this(id, workoutDate, amountOfSets, amountOfReps, exercise, status);
        this.order = order;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public LocalDate getWorkoutDate() {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(AssignmentStatus status){
        this.status = status;
    }

    public void setWorkoutDate(LocalDate workoutDate) {
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

    public void setOrder(Order order){
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return order.equals(that.order) &&
                amountOfSets == that.amountOfSets &&
                amountOfReps == that.amountOfReps &&
                Objects.equals(id, that.id) &&
                workoutDate.equals(that.workoutDate) &&
                exercise.equals(that.exercise) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, workoutDate, amountOfSets, amountOfReps, exercise, status);
    }
}