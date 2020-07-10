package com.epam.fitness.dto.mapper.impl;

import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.AssignmentDto;
import com.epam.fitness.entity.assignment.Assignment;
import com.epam.fitness.entity.assignment.AssignmentStatus;
import com.epam.fitness.entity.assignment.Exercise;
import com.epam.fitness.exception.DtoMappingException;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class AssignmentDtoMapperTest {

    private static final LocalDate VALID_DATE = LocalDate.of(2020, 11, 19);
    private static final Assignment FIRST_ASSIGNMENT =
            new Assignment(5, VALID_DATE, 15, 13, new Exercise(3), AssignmentStatus.NEW);
    private static final Assignment SECOND_ASSIGNMENT =
            new Assignment(18, VALID_DATE, 3, 19, new Exercise(5), AssignmentStatus.NEW);

    private static final AssignmentDto FIRST_ASSIGNMENT_DTO =
            new AssignmentDto(5, 3, 15, 13, VALID_DATE);
    private static final AssignmentDto SECOND_ASSIGNMENT_DTO =
            new AssignmentDto(18, 5, 3, 19, VALID_DATE);

    private static final List<Assignment> ASSIGNMENTS = Arrays.asList(
            FIRST_ASSIGNMENT,
            SECOND_ASSIGNMENT
    );
    private static final List<AssignmentDto> ASSIGNMENTS_DTO = Arrays.asList(
            FIRST_ASSIGNMENT_DTO,
            SECOND_ASSIGNMENT_DTO
    );
    private static final List<Assignment> NULL_ASSIGNMENTS = Arrays.asList(
            null,
            null,
            null
    );

    private ModelMapper modelMapper = new ModelMapper();
    private DtoMapper<Assignment, AssignmentDto> mapper = new AssignmentDtoMapper(modelMapper);

    @Test
    public void mapToDto() throws DtoMappingException {
        //given

        //when
        AssignmentDto actual = mapper.mapToDto(FIRST_ASSIGNMENT);

        //then
        assertEquals(FIRST_ASSIGNMENT_DTO, actual);
    }

    @Test(expected = DtoMappingException.class)
    public void mapToDtoWithDtoMappingException() throws DtoMappingException {
        //given
        final Assignment assignment = null;

        //when
        mapper.mapToDto(assignment);

        //then
    }

    @Test
    public void mapToEntity() throws DtoMappingException {
        //given

        //when
        Assignment actual = mapper.mapToEntity(SECOND_ASSIGNMENT_DTO);

        //then
        assertEquals(SECOND_ASSIGNMENT.getId(), actual.getId());
        assertEquals(SECOND_ASSIGNMENT.getWorkoutDate(), actual.getWorkoutDate());
        assertEquals(SECOND_ASSIGNMENT.getAmountOfSets(), actual.getAmountOfSets());
        assertEquals(SECOND_ASSIGNMENT.getAmountOfReps(), actual.getAmountOfReps());
        assertEquals(SECOND_ASSIGNMENT.getStatus(), actual.getStatus());
    }

    @Test(expected = DtoMappingException.class)
    public void mapToEntityWithDtoMappingException() throws DtoMappingException {
        //given
        final AssignmentDto assignmentDto = null;

        //when
        mapper.mapToEntity(assignmentDto);

        //then
    }

    @Test
    public void mapToDtoList() throws DtoMappingException {
        //given

        //when
        List<AssignmentDto> actual = mapper.mapToDto(ASSIGNMENTS);

        //then
        assertThat(actual, is(equalTo(ASSIGNMENTS_DTO)));
    }

    @Test(expected = DtoMappingException.class)
    public void mapToDtoListWithDtoMappingException() throws DtoMappingException {
        //given

        //when
        mapper.mapToDto(NULL_ASSIGNMENTS);

        //then
    }

}