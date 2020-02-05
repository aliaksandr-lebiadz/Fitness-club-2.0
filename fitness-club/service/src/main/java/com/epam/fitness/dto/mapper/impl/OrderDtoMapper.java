package com.epam.fitness.dto.mapper.impl;

import com.epam.fitness.dto.mapper.AbstractDtoMapper;
import com.epam.fitness.entity.OrderDto;
import com.epam.fitness.entity.order.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OrderDtoMapper extends AbstractDtoMapper<Order, OrderDto> {

    @Autowired
    public OrderDtoMapper(ModelMapper modelMapper){
        super(modelMapper, Order.class, OrderDto.class);
    }

}
