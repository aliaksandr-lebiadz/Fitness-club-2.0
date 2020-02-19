package com.epam.fitness.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "gym_membership")
public class GymMembership implements Identifiable, Serializable {

    private static final long serialVersionUID = -8624741859629195626L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "months_amount")
    @Positive
    private int monthsAmount;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    public GymMembership() {}

    public GymMembership(int id, int monthsAmount, BigDecimal price){
        this.id = id;
        this.monthsAmount = monthsAmount;
        this.price = price;
    }

    @Override
    public Integer getId(){ return id; }

    public int getMonthsAmount() {
        return monthsAmount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMonthsAmount(int monthsAmount) {
        this.monthsAmount = monthsAmount;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}