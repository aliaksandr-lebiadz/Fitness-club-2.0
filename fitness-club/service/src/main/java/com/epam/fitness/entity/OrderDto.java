package com.epam.fitness.entity;

import com.epam.fitness.entity.order.NutritionType;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class OrderDto implements Identifiable, Serializable {

    private static final long serialVersionUID = 2800486372398627490L;

    private Integer id;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;

    @Length(min = 10, max = 1000)
    private String feedback;
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

}
