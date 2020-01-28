package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.GymMembershipDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GymMembershipServiceImplTest {

    private static final GymMembership ONE_MONTH_MEMBERSHIP =
            new GymMembership(5, 1, BigDecimal.valueOf(39.99));
    private static final GymMembership ONE_YEAR_MEMBERSHIP =
            new GymMembership(7, 12, BigDecimal.valueOf(352.36));
    private static final GymMembershipDto ONE_MONTH_MEMBERSHIP_DTO =
            new GymMembershipDto(5, 1, BigDecimal.valueOf(39.99));
    private static final GymMembershipDto ONE_YEAR_MEMBERSHIP_DTO =
            new GymMembershipDto(7, 12, BigDecimal.valueOf(352.36));

    private static final List<GymMembership> GYM_MEMBERSHIPS =
            Arrays.asList(ONE_MONTH_MEMBERSHIP, ONE_YEAR_MEMBERSHIP);
    private static final List<GymMembershipDto> EXPECTED_GYM_MEMBERSHIPS_DTO =
            Arrays.asList(ONE_MONTH_MEMBERSHIP_DTO, ONE_YEAR_MEMBERSHIP_DTO);

    @Mock
    private Dao<GymMembership> gymMembershipDao;
    @Mock
    private DtoMapper<GymMembership, GymMembershipDto> gymMembershipDtoMapper;
    @InjectMocks
    private GymMembershipServiceImpl gymMembershipService;

    @Test
    public void testGetAll(){
        //given
        when(gymMembershipDao.getAll()).thenReturn(GYM_MEMBERSHIPS);
        when(gymMembershipDtoMapper.mapToDto(ONE_MONTH_MEMBERSHIP)).thenReturn(ONE_MONTH_MEMBERSHIP_DTO);
        when(gymMembershipDtoMapper.mapToDto(ONE_YEAR_MEMBERSHIP)).thenReturn(ONE_YEAR_MEMBERSHIP_DTO);

        //when
        List<GymMembershipDto> actual = gymMembershipService.getAll();

        //then
        assertThat(actual, is(equalTo(EXPECTED_GYM_MEMBERSHIPS_DTO)));

        verify(gymMembershipDao, times(1)).getAll();
        verify(gymMembershipDtoMapper, times(1)).mapToDto(ONE_MONTH_MEMBERSHIP);
        verify(gymMembershipDtoMapper, times(1)).mapToDto(ONE_YEAR_MEMBERSHIP);
        verifyNoMoreInteractions(gymMembershipDao, gymMembershipDtoMapper);
    }

}