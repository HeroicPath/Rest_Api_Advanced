package com.epam.esm.RestApiAdvanced.repository;

import com.epam.esm.RestApiAdvanced.entity.Order;
import com.epam.esm.RestApiAdvanced.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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