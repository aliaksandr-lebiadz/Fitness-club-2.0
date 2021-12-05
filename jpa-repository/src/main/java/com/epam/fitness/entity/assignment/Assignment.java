package com.epam.fitness.entity.assignment;

import com.epam.fitness.entity.Identifiable;
import com.epam.fitness.entity.order.Order;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "assignment")
public class Assignment implements Identifiable, Serializable {

    private static final long serialVersionUID = -3238821726259450444L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "workout_date")
    @NotNull
    private Date workoutDate;

    @Column(name = "amount_of_sets")
    @Min(1)
    @Max(100)
    private int amountOfSets;

    @Column(name = "amount_of_reps")
    @Min(1)
    @Max(100)
    private int amountOfReps;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AssignmentStatus status;

    public Assignment() {}

    public Assignment(Exercise exercise, int amountOfSets, int amountOfReps,
                       Date workoutDate, AssignmentStatus status){
        this.exercise = exercise;
        this.amountOfSets = amountOfSets;
        this.amountOfReps = amountOfReps;
        this.workoutDate = workoutDate;
        this.status = status;
    }

    public Assignment(Order order, Exercise exercise, int amountOfSets, int amountOfReps, Date workoutDate){
        this(exercise, amountOfSets, amountOfReps, workoutDate, AssignmentStatus.NEW);
        this.order = order;
    }

    public Assignment(Integer id, Order order, Exercise exercise, int amountOfSets,
                      int amountOfReps, Date workoutDate, AssignmentStatus status){
        this(order, exercise, amountOfSets, amountOfReps, workoutDate);
        this.id = id;
        this.status = status;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public Order getOrder() {
        return order;
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