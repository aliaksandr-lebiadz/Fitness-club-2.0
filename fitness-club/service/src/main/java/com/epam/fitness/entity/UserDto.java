package com.epam.fitness.entity;

import com.epam.fitness.entity.user.UserRole;

import java.io.Serializable;
import java.util.Objects;

public class UserDto implements Identifiable, Serializable {

    private static final long serialVersionUID = -6287929560717474040L;

    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String secondName;
    private UserRole role;
    private int discount;

    public UserDto() {}

    public UserDto(Integer id, int discount) {
        this.id = id;
        this.discount = discount;
    }

    public UserDto(Integer id, String email, String password, UserRole role,
                   String firstName, String secondName, int discount) {
        this(id, discount);
        this.email = email;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.secondName = secondName;
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

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public UserRole getRole() {
        return role;
    }

    public int getDiscount() {
        return discount;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return discount == userDto.discount &&
                Objects.equals(id, userDto.id) &&
                Objects.equals(email, userDto.email) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(firstName, userDto.firstName) &&
                Objects.equals(secondName, userDto.secondName) &&
                role == userDto.role;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", role=" + role +
                ", discount=" + discount +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, firstName, secondName, role, discount);
    }
}
