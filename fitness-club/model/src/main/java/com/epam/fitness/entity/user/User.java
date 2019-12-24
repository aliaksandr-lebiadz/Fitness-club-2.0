package com.epam.fitness.entity.user;

import com.epam.fitness.entity.Identifiable;

import java.io.Serializable;

public class User implements Identifiable, Serializable {

    private static final long serialVersionUID = 2767181524215678918L;

    private Integer id;
    private String email;
    private String password;
    private UserRole role;
    private String firstName;
    private String secondName;
    private int discount;

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