package Rest_Api_Advanced.service.impl;

import Rest_Api_Advanced.exception.LocalException;
import Rest_Api_Advanced.model.GiftCertificate;
import Rest_Api_Advanced.model.Order;
import Rest_Api_Advanced.model.User;
import Rest_Api_Advanced.repository.OrderRepository;
import Rest_Api_Advanced.repository.UserRepository;
import Rest_Api_Advanced.service.GiftCertificateService;
import Rest_Api_Advanced.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    OrderRepository orderRepository;
    UserRepository userRepository;
    GiftCertificateService giftCertificateService;

    @Override
    public Page<Order> getOrdersById(Long id, int page, int size) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new LocalException("no user found by this id", HttpStatus.NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);

        return orderRepository.findAll(pageable);
    }

    @Override
    public void createUser() {
        User user = new User();
        userRepository.save(user);
    }

    @Override
    public Order createOrder(Long id, Long giftCertificateId) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new LocalException("No user found by provided id", HttpStatus.NOT_FOUND));
        GiftCertificate giftCertificateServiceById = giftCertificateService.getById(giftCertificateId);
        Double cost = giftCertificateServiceById.getPrice();

        Order order = new Order();

        order.setUser(user);
        order.setCost(cost);
        order.setCreationTimeStamp(LocalDateTime.now());
        order.setGiftCertificate(giftCertificateServiceById);

        return orderRepository.save(order);
    }
}
