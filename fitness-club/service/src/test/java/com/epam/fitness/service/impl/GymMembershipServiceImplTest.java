package com.epam.fitness.service.impl;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.GymMembershipDto;
import com.epam.fitness.exception.DtoMappingException;
import com.epam.fitness.exception.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GymMembershipServiceImplTest {

    private static final List<GymMembership> GYM_MEMBERSHIPS = Arrays.asList(
            new GymMembership(5, 1, BigDecimal.valueOf(39.99)),
            new GymMembership(7, 12, BigDecimal.valueOf(352.36)));
    private static final List<GymMembershipDto> EXPECTED_GYM_MEMBERSHIPS_DTO = Arrays.asList(
            new GymMembershipDto(5, 1, BigDecimal.valueOf(39.99)),
            new GymMembershipDto(7, 12, BigDecimal.valueOf(352.36)));

    @Mock
    private Dao<GymMembership> gymMembershipDao;
    @Mock
    private DtoMapper<GymMembership, GymMembershipDto> gymMembershipMapper;
    @InjectMocks
    private GymMembershipServiceImpl gymMembershipService;

    @Before
    public void createMocks() throws DtoMappingException {
        MockitoAnnotations.initMocks(this);

        when(gymMembershipDao.getAll()).thenReturn(GYM_MEMBERSHIPS);
        when(gymMembershipMapper.mapToDto(GYM_MEMBERSHIPS)).thenReturn(EXPECTED_GYM_MEMBERSHIPS_DTO);
    }

    @Test
    public void getAll() throws ServiceException {
        //given

        //when
        List<GymMembershipDto> actual = gymMembershipService.getAll();

        //then
        assertThat(actual, is(equalTo(EXPECTED_GYM_MEMBERSHIPS_DTO)));

        verify(gymMembershipDao, times(1)).getAll();
        verify(gymMembershipMapper, times(1)).mapToDto(GYM_MEMBERSHIPS);
    }

    @After
    public void verifyMocks() {
        verifyNoMoreInteractions(gymMembershipDao, gymMembershipMapper);
        reset(gymMembershipDao, gymMembershipMapper);
    }

}