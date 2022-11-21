package com.epam.esm.Rest_Api_Advanced.service;

import com.epam.esm.Rest_Api_Advanced.exception.LocalException;
import com.epam.esm.Rest_Api_Advanced.model.GiftCertificate;
import com.epam.esm.Rest_Api_Advanced.model.Order;
import com.epam.esm.Rest_Api_Advanced.model.User;
import com.epam.esm.Rest_Api_Advanced.repository.OrderRepository;
import com.epam.esm.Rest_Api_Advanced.repository.UserRepository;
import com.epam.esm.Rest_Api_Advanced.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.Rest_Api_Advanced.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getOrdersById() {
        List<Object> list = Arrays.asList(new Order[]{new Order()});
        PageImpl<Object> objects = new PageImpl<>(list);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("cost"));

        when(orderRepository.findByUser_Id(1L, pageable)).thenReturn((Page) objects);
        when(orderRepository.findByUser_Id(2L, pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));

        Page<Order> ordersById = userService.getOrdersById(1L, 0, 10);

        verify(orderRepository).findByUser_Id(any(), any());
        Assertions.assertThat(ordersById).isNotNull();
        Assertions.assertThatThrownBy(() -> userService.getOrdersById(2L, 0, 10)).isInstanceOf(LocalException.class);
    }

    @Test
    void shouldCreateUser() {
        userService.createUser();

        verify(userRepository).save(any());
    }

    @Test
    void createOrder() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setPrice(100.);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(new User()));
        when(giftCertificateService.getById(1L)).thenReturn(giftCertificate);

        userService.createOrder(1L, 1L);

        verify(userRepository).findById(1L);
        verify(giftCertificateService).getById(1L);
        verify(orderRepository).save(any());

        Assertions.assertThatThrownBy(() -> userService.createOrder(2L, 1L)).isInstanceOf(LocalException.class);
    }
}