package com.epam.fitness.controller;

import com.epam.fitness.entity.GymMembershipDto;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.GymMembershipService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GymMembershipControllerTest extends AbstractControllerTest{

    private static final String GYM_MEMBERSHIPS_URL = "/gym-memberships";
    private static final List<GymMembershipDto> GYM_MEMBERSHIPS_DTO = Arrays.asList(
            new GymMembershipDto(1, 6, BigDecimal.valueOf(356.99)),
            new GymMembershipDto(3, 12, BigDecimal.valueOf(673.99)),
            new GymMembershipDto(6, 3, BigDecimal.valueOf(179.99))
    );

    @Mock
    private GymMembershipService gymMembershipService;
    @InjectMocks
    private GymMembershipController controller;

    @Before
    public void setUp() {
        configureMockMvc(controller);
    }

    @Before
    public void createMocks() throws ServiceException {
        when(gymMembershipService.getAll()).thenReturn(GYM_MEMBERSHIPS_DTO);
    }

    @Test
    public void getExercises() throws Exception {
        //given

        //when
        String actualJson = mockMvc
                .perform(get(GYM_MEMBERSHIPS_URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        GymMembershipDto[] gymMembershipsDto = mapFromJson(actualJson, GymMembershipDto[].class);
        List<GymMembershipDto> actual = Arrays.asList(gymMembershipsDto);

        //then
        assertThat(actual, is(equalTo(GYM_MEMBERSHIPS_DTO)));

        verify(gymMembershipService, times(1)).getAll();
        verifyNoMoreInteractions(gymMembershipService);
    }

}