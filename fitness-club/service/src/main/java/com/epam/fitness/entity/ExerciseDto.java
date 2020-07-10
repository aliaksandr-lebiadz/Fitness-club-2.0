package com.epam.fitness.entity;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseDto that = (ExerciseDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
