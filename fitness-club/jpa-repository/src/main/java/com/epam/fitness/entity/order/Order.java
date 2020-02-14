package com.epam.fitness.entity.order;

import com.epam.fitness.entity.Identifiable;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.user.User;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "client_order")
public class Order implements Identifiable, Serializable {

    private static final long serialVersionUID = 8562547550211367098L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false, updatable = false)
    private User client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainer_id", nullable = false, updatable = false)
    private User trainer;

    @Column(name = "begin_date")
    @NotNull
    private LocalDateTime beginDate;

    @Column(name = "end_date")
    @NotNull
    private LocalDateTime endDate;

    @Length(min = 10, max = 1000)
    private String feedback;

    @PositiveOrZero
    @NotNull
    private BigDecimal price;

    @Column(name = "nutrition_type")
    @Enumerated(EnumType.STRING)
    private NutritionType nutritionType;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Assignment> assignments;

    public Order() {}

    public static Builder createBuilder(){
        return new Order().new Builder();
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

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public User getClient(){
        return client;
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

    public void setClient(User client) {
        this.client = client;
    }

    public void setTrainer(User trainer) {
        this.trainer = trainer;
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

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public class Builder{

        private Builder() {
            //private constructor
        }

        public Builder setId(Integer id){
            Order.this.id = id;
            return this;
        }

        public Builder setTrainer(User trainer){
            Order.this.trainer = trainer;
            return this;
        }

        public Builder setBeginDate(LocalDateTime beginDate){
            Order.this.beginDate = beginDate;
            return this;
        }

        public Builder setEndDate(LocalDateTime endDate){
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

        public Builder setClient(User client) {
            Order.this.client = client;
            return this;
        }

        public Order build(){
            return Order.this;
        }
    }
}