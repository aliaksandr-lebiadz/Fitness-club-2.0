package com.epam.fitness.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GymMembershipDto that = (GymMembershipDto) o;
        return monthsAmount == that.monthsAmount &&
                Objects.equals(id, that.id) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, monthsAmount, price);
    }
}
