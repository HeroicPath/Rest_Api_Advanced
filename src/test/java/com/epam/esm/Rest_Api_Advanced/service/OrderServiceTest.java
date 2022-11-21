package com.epam.esm.Rest_Api_Advanced.service;

import com.epam.esm.Rest_Api_Advanced.exception.LocalException;
import com.epam.esm.Rest_Api_Advanced.model.Order;
import com.epam.esm.Rest_Api_Advanced.repository.OrderRepository;
import com.epam.esm.Rest_Api_Advanced.service.impl.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void shouldGetAll() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("creationTimeStamp"));
        orderService.getAll(0, 10);

        verify(orderRepository).findAll(pageable);
    }

    @Test
    void shouldGetById() {
        Order orderToPass = new Order();
        orderToPass.setCost(2.);
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(orderToPass));

        Order byId = orderService.getById(1L);

        verify(orderRepository).findById(1L);

        Assertions.assertThat(byId.getCost()).isEqualTo(orderToPass.getCost());
        Assertions.assertThatThrownBy(() -> orderService.getById(2L)).isInstanceOf(LocalException.class);
    }
}