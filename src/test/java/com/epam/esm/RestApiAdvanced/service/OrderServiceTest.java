package com.epam.esm.RestApiAdvanced.service;

import com.epam.esm.RestApiAdvanced.dto.OrderDto;
import com.epam.esm.RestApiAdvanced.entity.Order;
import com.epam.esm.RestApiAdvanced.exception.LocalException;
import com.epam.esm.RestApiAdvanced.hateoas.assembler.OrderAssembler;
import com.epam.esm.RestApiAdvanced.mapper.OrderMapper;
import com.epam.esm.RestApiAdvanced.repository.OrderRepository;
import com.epam.esm.RestApiAdvanced.service.impl.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private PagedResourcesAssembler<Order> pagedResourcesAssembler;

    @Mock
    private OrderAssembler orderAssembler;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void shouldGetAll() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("cost"));
        PageImpl<Order> page = new PageImpl<>(Arrays.asList(new Order()));

        when(orderRepository.findAll((Pageable) any())).thenReturn(page);
        when(pagedResourcesAssembler.toModel(page, orderAssembler)).thenReturn(PagedModel.empty());

        PagedModel<OrderDto> orderDtos = orderService.getAll(0, 10);

        verify(orderRepository).findAll(pageable);

        Assertions.assertThat(orderDtos).isNotNull();
    }

    @Test
    void shouldGetById() {
        Order orderToPass = new Order();
        orderToPass.setCost(2.);
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(orderToPass));
        when(orderMapper.toDto(any())).thenReturn(new OrderDto(orderToPass.getCost()));

        OrderDto byId = orderService.getById(1L);

        verify(orderRepository).findById(1L);

        Assertions.assertThat(byId.getCost()).isEqualTo(orderToPass.getCost());
        Assertions.assertThatThrownBy(() -> orderService.getById(2L)).isInstanceOf(LocalException.class);
    }

    @Test
    void shouldDeleteById() {
        orderService.deleteById(1L);

        verify(orderRepository).deleteById(1L);
    }
}