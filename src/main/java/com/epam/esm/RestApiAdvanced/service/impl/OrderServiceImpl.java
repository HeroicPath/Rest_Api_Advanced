package com.epam.esm.RestApiAdvanced.service.impl;

import com.epam.esm.RestApiAdvanced.dto.OrderDto;
import com.epam.esm.RestApiAdvanced.exception.LocalException;
import com.epam.esm.RestApiAdvanced.entity.Order;
import com.epam.esm.RestApiAdvanced.hateoas.assembler.OrderAssembler;
import com.epam.esm.RestApiAdvanced.mapper.OrderMapper;
import com.epam.esm.RestApiAdvanced.repository.OrderRepository;
import com.epam.esm.RestApiAdvanced.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderAssembler orderAssembler;
    private final PagedResourcesAssembler<Order> pagedResourcesAssembler;

    @Override
    public PagedModel<OrderDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("cost"));
        Page<Order> orders = orderRepository.findAll(pageable);

        if (page > orders.getTotalPages() - 1) {
            throw new LocalException("this page doesn't exist", HttpStatus.BAD_REQUEST);
        }

        return  pagedResourcesAssembler.toModel(orders, orderAssembler);
    }

    @Override
    public OrderDto getById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new LocalException("order by this id is not found"
                        , HttpStatus.NOT_FOUND));
        return orderMapper.toDto(order);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
