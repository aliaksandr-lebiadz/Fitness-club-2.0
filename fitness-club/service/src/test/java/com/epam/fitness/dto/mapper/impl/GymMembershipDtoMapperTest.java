package com.epam.fitness.dto.mapper.impl;

import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.GymMembershipDto;
import com.epam.fitness.exception.DtoMappingException;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class GymMembershipDtoMapperTest {

    private static final GymMembership FIRST_GYM_MEMBERSHIP
            = new GymMembership(1, 6, BigDecimal.valueOf(110.56));
    private static final GymMembership SECOND_GYM_MEMBERSHIP
            = new GymMembership(5, 3, BigDecimal.valueOf(62.99));
    private static final GymMembership THIRD_GYM_MEMBERSHIP
            = new GymMembership(11, 12, BigDecimal.valueOf(205.89));

    private static final GymMembershipDto FIRST_GYM_MEMBERSHIP_DTO
            = new GymMembershipDto(1, 6, BigDecimal.valueOf(110.56));
    private static final GymMembershipDto SECOND_GYM_MEMBERSHIP_DTO
            = new GymMembershipDto(5, 3, BigDecimal.valueOf(62.99));
    private static final GymMembershipDto THIRD_GYM_MEMBERSHIP_DTO
            = new GymMembershipDto(11, 12, BigDecimal.valueOf(205.89));

    private static final List<GymMembership> GYM_MEMBERSHIPS = Arrays.asList(
            FIRST_GYM_MEMBERSHIP,
            SECOND_GYM_MEMBERSHIP,
            THIRD_GYM_MEMBERSHIP
    );
    private static final List<GymMembershipDto> GYM_MEMBERSHIPS_DTO  = Arrays.asList(
            FIRST_GYM_MEMBERSHIP_DTO,
            SECOND_GYM_MEMBERSHIP_DTO,
            THIRD_GYM_MEMBERSHIP_DTO
    );
    private static final List<GymMembership> NULL_GYM_MEMBERSHIPS = Arrays.asList(
            null,
            null
    );

    private ModelMapper modelMapper = new ModelMapper();
    private DtoMapper<GymMembership, GymMembershipDto> mapper = new GymMembershipDtoMapper(modelMapper);

    @Test
    public void mapToDto() throws DtoMappingException {
        //given

        //when
        GymMembershipDto actual = mapper.mapToDto(FIRST_GYM_MEMBERSHIP);

        //then
        assertEquals(FIRST_GYM_MEMBERSHIP_DTO, actual);
    }

    @Test(expected = DtoMappingException.class)
    public void mapToDtoWithDtoMappingException() throws DtoMappingException {
        //given
        final GymMembership gymMembership = null;

        //when
        mapper.mapToDto(gymMembership);

        //then
    }

    @Test
    public void mapToEntity() throws DtoMappingException {
        //given

        //when
        GymMembership actual = mapper.mapToEntity(THIRD_GYM_MEMBERSHIP_DTO);

        //then
        assertEquals(THIRD_GYM_MEMBERSHIP.getId(), actual.getId());
        assertEquals(THIRD_GYM_MEMBERSHIP.getMonthsAmount(), actual.getMonthsAmount());
        assertEquals(THIRD_GYM_MEMBERSHIP.getPrice(), actual.getPrice());
    }

    @Test(expected = DtoMappingException.class)
    public void mapToEntityWithDtoMappingException() throws DtoMappingException {
        //given
        final GymMembershipDto gymMembershipDto = null;

        //when
        mapper.mapToEntity(gymMembershipDto);

        //then
    }

    @Test
    public void mapToDtoList() throws DtoMappingException {
        //given

        //when
        List<GymMembershipDto> actual = mapper.mapToDto(GYM_MEMBERSHIPS);

        //then
        assertThat(actual, is(equalTo(GYM_MEMBERSHIPS_DTO)));
    }

    @Test(expected = DtoMappingException.class)
    public void mapToDtoListWithDtoMappingException() throws DtoMappingException {
        //given

        //when
        mapper.mapToDto(NULL_GYM_MEMBERSHIPS);

        //then
    }

}