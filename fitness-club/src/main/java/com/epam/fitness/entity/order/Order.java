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

    private Order(){
        //private constructor
    }

    public static Builder createBuilder(){
        return new Order().new Builder();
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

    public class Builder{

        private Builder() {
            //private constructor
        }

        public Builder setId(Integer id){
            Order.this.id = id;
            return this;
        }

        public Builder setClientId(int clientId){
            Order.this.clientId = clientId;
            return this;
        }

        public Builder setTrainerId(int trainerId){
            Order.this.trainerId = trainerId;
            return this;
        }

        public Builder setBeginDate(Date beginDate){
            Order.this.beginDate = beginDate;
            return this;
        }

        public Builder setEndDate(Date endDate){
            Order.this.endDate = endDate;
            return this;
        }

        public Builder setFeedback(String feedback){
            Order.this.feedback = feedback;
            return this;
        }

        public Builder setPrice(BigDecimal price){
            Order.this.price = price;
            return this;
        }

        public Builder setNutritionType(NutritionType nutritionType){
            Order.this.nutritionType = nutritionType;
            return this;
        }

        public Order build(){
            return Order.this;
        }
    }
}