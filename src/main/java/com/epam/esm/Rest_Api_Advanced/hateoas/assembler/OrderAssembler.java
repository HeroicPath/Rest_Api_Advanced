package com.epam.esm.Rest_Api_Advanced.hateoas.assembler;

import com.epam.esm.Rest_Api_Advanced.controller.OrderController;
import com.epam.esm.Rest_Api_Advanced.dto.GiftCertificateDto;
import com.epam.esm.Rest_Api_Advanced.dto.OrderDto;
import com.epam.esm.Rest_Api_Advanced.model.Order;
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
