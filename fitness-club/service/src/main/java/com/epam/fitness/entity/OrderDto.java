package com.epam.fitness.entity;

import com.epam.fitness.entity.order.NutritionType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class OrderDto implements Identifiable, Serializable {

    private static final long serialVersionUID = 2800486372398627490L;

    private Integer id;
    private LocalDateTime beginDate;
    @FutureOrPresent
    private LocalDateTime endDate;

    @Length(min = 10, max = 1000)
    private String feedback;
    @NotNull
    @PositiveOrZero
    private BigDecimal price;
    private NutritionType nutritionType;

    public OrderDto() {}

    public OrderDto(int id, String feedback) {
        this.id = id;
        this.feedback = feedback;
    }

    public OrderDto(int id, NutritionType nutritionType){
        this.id = id;
        this.nutritionType = nutritionType;
    }

    public OrderDto(int id, String feedback, BigDecimal price) {
        this(id, feedback);
        this.price = price;
    }

    public OrderDto(int id, LocalDateTime beginDate, LocalDateTime endDate, BigDecimal price){
        this.id = id;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.price = price;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getFeedback() {
        return feedback;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public NutritionType getNutritionType() {
        return nutritionType;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setNutritionType(NutritionType nutritionType) {
        this.nutritionType = nutritionType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(id, orderDto.id) &&
                Objects.equals(beginDate, orderDto.beginDate) &&
                Objects.equals(endDate, orderDto.endDate) &&
                Objects.equals(feedback, orderDto.feedback) &&
                Objects.equals(price, orderDto.price) &&
                nutritionType == orderDto.nutritionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, beginDate, endDate, feedback, price, nutritionType);
    }
}
