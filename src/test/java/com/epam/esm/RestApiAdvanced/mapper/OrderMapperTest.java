package com.epam.esm.RestApiAdvanced.mapper;

import com.epam.esm.RestApiAdvanced.dto.OrderDto;
import com.epam.esm.RestApiAdvanced.entity.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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