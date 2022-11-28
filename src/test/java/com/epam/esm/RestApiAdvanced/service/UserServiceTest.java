package com.epam.esm.RestApiAdvanced.service;

import com.epam.esm.RestApiAdvanced.dto.OrderDto;
import com.epam.esm.RestApiAdvanced.entity.GiftCertificate;
import com.epam.esm.RestApiAdvanced.entity.Order;
import com.epam.esm.RestApiAdvanced.entity.User;
import com.epam.esm.RestApiAdvanced.exception.LocalException;
import com.epam.esm.RestApiAdvanced.mapper.OrderMapper;
import com.epam.esm.RestApiAdvanced.repository.GiftCertificateRepository;
import com.epam.esm.RestApiAdvanced.repository.OrderRepository;
import com.epam.esm.RestApiAdvanced.repository.UserRepository;
import com.epam.esm.RestApiAdvanced.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PagedResourcesAssembler<User> pagedResourcesAssembler;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getOrdersById() {
        List<Object> list = Arrays.asList(new Order[]{new Order()});
        PageImpl<Object> objects = new PageImpl<>(list);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("cost"));

        when(orderRepository.queryAllByUser_Id(1L, pageable)).thenReturn((Page) objects);
        when(orderRepository.queryAllByUser_Id(2L, pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));
        when(pagedResourcesAssembler.toModel(any(), (RepresentationModelAssembler) any())).thenReturn(PagedModel.empty());

        PagedModel<OrderDto> ordersById = userService.getOrdersById(1L, 0, 10);

        verify(orderRepository).queryAllByUser_Id(any(), any());

        Assertions.assertThat(ordersById).isNotNull();
        Assertions.assertThatThrownBy(() -> userService.getOrdersById(2L, 0, 10)).isInstanceOf(LocalException.class);
    }

    @Test
    void shouldCreateUser() {
        userService.createUser();

        verify(userRepository).save(any());
    }

    @Test
    void shouldCreateOrder() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setPrice(100.);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(new User()));
        when(giftCertificateRepository.findById(1L)).thenReturn(java.util.Optional.of(giftCertificate));
        when(orderMapper.toDto(any())).thenReturn(new OrderDto());

        OrderDto order = userService.createOrder(1L, 1L);

        verify(userRepository).findById(1L);
        verify(giftCertificateRepository).findById(1L);
        verify(orderRepository).save(any());

        Assertions.assertThat(order).isNotNull();
        Assertions.assertThatThrownBy(() -> userService.createOrder(2L, 1L)).isInstanceOf(LocalException.class);
    }

    @Test
    void shouldDeleteById() {
        userService.deleteById(1L);

        verify(userRepository).deleteById(1L);
    }
}