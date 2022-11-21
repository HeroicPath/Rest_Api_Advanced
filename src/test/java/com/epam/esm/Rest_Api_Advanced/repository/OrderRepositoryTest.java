package com.epam.esm.Rest_Api_Advanced.repository;

import com.epam.esm.Rest_Api_Advanced.model.Order;
import com.epam.esm.Rest_Api_Advanced.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldFindByUser_Id() {
        User userToSave = new User(1L, null, null, null);
        userRepository.save(userToSave);

        Order orderToSave = new Order();
        orderToSave.setUser(userToSave);
        orderRepository.save(orderToSave);

        Page<Order> byUser_id = orderRepository.findByUser_Id(1L, PageRequest.of(0, 10));
        Page<Order> orders = orderRepository.findByUser_Id(2L, PageRequest.of(0, 10));

        Assertions.assertThat(byUser_id.isEmpty()).isFalse();
        Assertions.assertThat(orders.isEmpty()).isTrue();
    }
}