package com.epam.fitness.entity.assignment;

import com.epam.fitness.entity.Identifiable;

import java.io.Serializable;

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

}