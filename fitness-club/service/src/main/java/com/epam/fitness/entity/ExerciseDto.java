package com.epam.fitness.entity;

import java.io.Serializable;

public class ExerciseDto implements Identifiable, Serializable {

    private static final long serialVersionUID = -4418618624029164372L;

    private Integer id;
    private String name;

    public ExerciseDto() {}

    public ExerciseDto(int id){
        this.id = id;
    }

    public ExerciseDto(int id, String name){
        this(id);
        this.name = name;
    }

    @Override
    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
