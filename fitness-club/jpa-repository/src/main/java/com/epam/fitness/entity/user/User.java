package com.epam.fitness.entity.user;

import com.epam.fitness.entity.Identifiable;
import com.epam.fitness.entity.order.Order;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "fitness_user")
public class User implements Identifiable, Serializable {

    private static final long serialVersionUID = 2767181524215678918L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    private int discount;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Order> orders;

    public User() {}

    public User(Integer id, String email, String password, UserRole role,
                String firstName, String secondName, int discount) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.secondName = secondName;
        this.discount = discount;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount){
        this.discount = discount;
    }
}