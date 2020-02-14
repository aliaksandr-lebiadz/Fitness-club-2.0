package com.epam.fitness.controller;

import com.epam.fitness.entity.ExerciseDto;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService){
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public List<ExerciseDto> getExercises() throws ServiceException {
        return exerciseService.getAll();
    }

}
