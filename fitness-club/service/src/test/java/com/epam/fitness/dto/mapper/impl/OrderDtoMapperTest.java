package com.epam.fitness.dto.mapper.impl;

import com.epam.fitness.dto.mapper.DtoMapper;
import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.exception.DtoMappingException;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class OrderDtoMapperTest {

    private static final LocalDateTime VALID_DATE = LocalDateTime.of(2020, 12, 11, 17, 33);
    private static final Order FIRST_ORDER = Order
            .createBuilder()
            .setId(5)
            .setBeginDate(VALID_DATE)
            .setEndDate(VALID_DATE)
            .setPrice(BigDecimal.valueOf(15.35))
            .build();
    private static final Order SECOND_ORDER = Order
            .createBuilder()
            .setId(3)
            .setBeginDate(VALID_DATE)
            .setEndDate(VALID_DATE)
            .setPrice(BigDecimal.valueOf(155.18))
            .build();

    private static final OrderDto FIRST_ORDER_DTO = new OrderDto(5, VALID_DATE, VALID_DATE, BigDecimal.valueOf(15.35));
    private static final OrderDto SECOND_ORDER_DTO = new OrderDto(3, VALID_DATE, VALID_DATE, BigDecimal.valueOf(155.18));

    private static final List<Order> ORDERS = Arrays.asList(
            FIRST_ORDER,
            SECOND_ORDER
    );
    private static final List<OrderDto> ORDERS_DTO = Arrays.asList(
            FIRST_ORDER_DTO,
            SECOND_ORDER_DTO
    );
    private static final List<Order> NULL_ORDERS = Arrays.asList(
            null,
            null
    );

    private ModelMapper modelMapper = new ModelMapper();
    private DtoMapper<Order, OrderDto> mapper = new OrderDtoMapper(modelMapper);

    @Test
    public void mapToDto() throws DtoMappingException {
        //given

        //when
        OrderDto actual = mapper.mapToDto(FIRST_ORDER);

        //then
        assertEquals(FIRST_ORDER_DTO, actual);
    }

    @Test(expected = DtoMappingException.class)
    public void mapToDtoWithDtoMappingException() throws DtoMappingException {
        //given
        final Order order = null;

        //when
        mapper.mapToDto(order);

        //then
    }

    @Test
    public void mapToEntity() throws DtoMappingException {
        //given

        //when
        Order actual = mapper.mapToEntity(SECOND_ORDER_DTO);

        //then
        assertEquals(SECOND_ORDER.getId(), actual.getId());
        assertEquals(SECOND_ORDER.getBeginDate(), actual.getBeginDate());
        assertEquals(SECOND_ORDER.getEndDate(), actual.getEndDate());
        assertEquals(SECOND_ORDER.getPrice(), actual.getPrice());
    }

    @Test(expected = DtoMappingException.class)
    public void mapToEntityWithDtoMappingException() throws DtoMappingException {
        //given
        final OrderDto orderDto = null;

        //when
        mapper.mapToEntity(orderDto);

        //then
    }

    @Test
    public void mapToDtoList() throws DtoMappingException {
        //given

        //when
        List<OrderDto> actual = mapper.mapToDto(ORDERS);

        //then
        assertThat(actual, is(equalTo(ORDERS_DTO)));
    }

    @Test(expected = DtoMappingException.class)
    public void mapToDtoListWithDtoMappingException() throws DtoMappingException {
        //given

        //when
        mapper.mapToDto(NULL_ORDERS);

        //then
    }

}