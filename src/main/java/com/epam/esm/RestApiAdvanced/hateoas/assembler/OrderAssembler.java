package com.epam.esm.RestApiAdvanced.hateoas.assembler;

import com.epam.esm.RestApiAdvanced.controller.OrderController;
import com.epam.esm.RestApiAdvanced.dto.OrderDto;
import com.epam.esm.RestApiAdvanced.entity.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class OrderAssembler extends RepresentationModelAssemblerSupport<Order, OrderDto> {

    public OrderAssembler() {
        super(OrderController.class, OrderDto.class);
    }

    @Override
    public OrderDto toModel(Order entity) {
        OrderDto model = new OrderDto();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
