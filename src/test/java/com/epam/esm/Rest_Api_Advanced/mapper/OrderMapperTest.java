package com.epam.esm.Rest_Api_Advanced.mapper;

import com.epam.esm.Rest_Api_Advanced.dto.OrderDto;
import com.epam.esm.Rest_Api_Advanced.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    private OrderMapper orderMapper;
    private Order order;
    private final double COST = 2.;

    @BeforeEach
    void setUp() {
        orderMapper = new OrderMapperImpl();
        order = new Order();
        order.setCost(COST);
    }

    @Test
    void shouldConvertToDto() {
        OrderDto orderDto = orderMapper.toDto(order);

        Assertions.assertThat(orderDto.getCost()).isEqualTo(order.getCost());
    }
}