package com.epam.esm.Rest_Api_Advanced.repository;

import com.epam.esm.Rest_Api_Advanced.model.Order;
import com.epam.esm.Rest_Api_Advanced.model.User;
import org.assertj.core.api.Assertions;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    void shouldGetTheWealthiestUsersId() {
        User wealthiest = new User();
        User notWealthiest = new User();

        Order order = new Order();
        order.setUser(wealthiest);
        order.setCost(200.);
        Order anotherOrder = new Order();
        anotherOrder.setUser(notWealthiest);
        anotherOrder.setCost(2.);

        userRepository.save(wealthiest);
        userRepository.save(notWealthiest);
        orderRepository.save(order);
        orderRepository.save(anotherOrder);

        Long theWealthiestUsersId = userRepository.getTheWealthiestUsersId();

        Assertions.assertThat(theWealthiestUsersId).isEqualTo(wealthiest.getId());
        Assertions.assertThat(theWealthiestUsersId).isNotEqualTo(notWealthiest.getId());
    }
}