package com.epam.fitness.entity;

import javax.persistence.*;
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
    private int monthsAmount;
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
}