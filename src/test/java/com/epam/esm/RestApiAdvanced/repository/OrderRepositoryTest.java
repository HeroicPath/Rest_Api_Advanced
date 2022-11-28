package com.epam.esm.RestApiAdvanced.repository;

import com.epam.esm.RestApiAdvanced.entity.Order;
import com.epam.esm.RestApiAdvanced.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

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
    void shouldFindAllByUser_Id(){
        User save = userRepository.save(new User(1L, null, null, null));

        Order orderToSave = new Order();
        orderToSave.setUser(save);
        orderRepository.save(orderToSave);

        List<Order> byUser_id = orderRepository.findAllByUser_Id(1L);
        List<Order> orders = orderRepository.findAllByUser_Id(2L);

        Assertions.assertThat(byUser_id.isEmpty()).isFalse();
        Assertions.assertThat(orders.isEmpty()).isTrue();
    }

    @Test
    void shouldFindByUser_Id() {
        User save = userRepository.save(new User(1L, null, null, null));

        Order orderToSave = new Order();
        orderToSave.setUser(save);
        orderRepository.save(orderToSave);

        Page<Order> byUser_id = orderRepository.queryAllByUser_Id(save.getId(), PageRequest.of(0, 10));
        Page<Order> orders = orderRepository.queryAllByUser_Id(5L, PageRequest.of(0, 10));

        Assertions.assertThat(byUser_id.isEmpty()).isFalse();
        Assertions.assertThat(orders.isEmpty()).isTrue();
    }
}