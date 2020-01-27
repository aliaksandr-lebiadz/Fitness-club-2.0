package com.epam.fitness.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class GymMembershipDto implements Identifiable, Serializable {

    private static final long serialVersionUID = -5916987032170066088L;

    private Integer id;
    private int monthsAmount;
    private BigDecimal price;

    public GymMembershipDto() {}

    public GymMembershipDto(int id, int monthsAmount, BigDecimal price){
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
