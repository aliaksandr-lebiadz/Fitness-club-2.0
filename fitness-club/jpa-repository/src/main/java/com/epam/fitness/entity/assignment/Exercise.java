package com.epam.fitness.entity.assignment;

import com.epam.fitness.entity.Identifiable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "exercise")
public class Exercise implements Identifiable, Serializable {

    private static final long serialVersionUID = 875330555003957031L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Assignment> assignments;

    public Exercise() {}

    public Exercise(int id){
        this.id = id;
    }

    public Exercise(int id, String name){
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

    public List<Assignment> getAssignments(){
        return assignments;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return Objects.equals(id, exercise.id) &&
                Objects.equals(name, exercise.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}