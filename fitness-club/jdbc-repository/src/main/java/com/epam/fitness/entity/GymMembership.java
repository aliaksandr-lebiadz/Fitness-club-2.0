package com.epam.fitness.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class GymMembership implements Identifiable, Serializable {

    private static final long serialVersionUID = -8624741859629195626L;

    private Integer id;
    private int monthsAmount;
    private BigDecimal price;

    public GymMembership(int id, int monthsAmount, BigDecimal price){
        this.id = id;
        this.monthsAmount = monthsAmount;
        this.price = price;
    }

    public int getMonthsAmount() {
        return monthsAmount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public Integer getId(){ return id; }
}