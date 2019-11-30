package com.epam.fitness.entity.order;

import com.epam.fitness.entity.Identifiable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Order implements Identifiable, Serializable {

    private static final long serialVersionUID = 8562547550211367098L;

    private Integer id;
    private int clientId;
    private int trainerId;
    private Date beginDate;
    private Date endDate;
    private String feedback;
    private BigDecimal price;
    private NutritionType nutritionType;

    public Order(int clientId, Date endDate, BigDecimal price){
        this.clientId = clientId;
        this.endDate = endDate;
        this.price = price;
        this.beginDate = new Date();
    }

    public Order(Integer id, int clientId, int trainerId, Date beginDate, Date endDate, BigDecimal price,
                 String feedback, NutritionType nutritionType){
        this(clientId, endDate, price);
        this.id = id;
        this.trainerId = trainerId;
        this.beginDate = beginDate;
        this.feedback = feedback;
        this.nutritionType = nutritionType;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public int getTrainerId(){
        return trainerId;
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
}