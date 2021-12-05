package com.epam.fitness.entity;

import com.epam.fitness.entity.order.NutritionType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderDto implements Identifiable, Serializable {

    private static final long serialVersionUID = 2800486372398627490L;

    private Integer id;
    private Date beginDate;
    private Date endDate;
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

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
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

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
