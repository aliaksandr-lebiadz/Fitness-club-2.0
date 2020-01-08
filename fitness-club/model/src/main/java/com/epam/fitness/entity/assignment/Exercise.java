package com.epam.fitness.entity.assignment;

import com.epam.fitness.entity.Identifiable;

import java.io.Serializable;
import java.util.Objects;

public class Exercise implements Identifiable, Serializable {

    private static final long serialVersionUID = 875330555003957031L;

    private Integer id;
    private String name;

    public Exercise(int id){
        this.id = id;
    }

    public Exercise(String name){
        this.name = name;
    }

    public Exercise(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return Objects.equals(id, exercise.id) &&
                Objects.equals(name, exercise.name);
    }
}