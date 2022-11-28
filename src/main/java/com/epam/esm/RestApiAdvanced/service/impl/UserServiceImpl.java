package com.epam.esm.RestApiAdvanced.service.impl;

import com.epam.esm.RestApiAdvanced.dto.OrderDto;
import com.epam.esm.RestApiAdvanced.entity.GiftCertificate;
import com.epam.esm.RestApiAdvanced.entity.Order;
import com.epam.esm.RestApiAdvanced.entity.User;
import com.epam.esm.RestApiAdvanced.exception.LocalException;
import com.epam.esm.RestApiAdvanced.hateoas.assembler.OrderAssembler;
import com.epam.esm.RestApiAdvanced.mapper.OrderMapper;
import com.epam.esm.RestApiAdvanced.repository.GiftCertificateRepository;
import com.epam.esm.RestApiAdvanced.repository.OrderRepository;
import com.epam.esm.RestApiAdvanced.repository.UserRepository;
import com.epam.esm.RestApiAdvanced.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository giftCertificateRepository;
    private final OrderAssembler orderAssembler;
    private final PagedResourcesAssembler<Order> pagedResourcesAssembler;

    @Override
    public PagedModel<OrderDto> getOrdersById(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("cost"));

        Page<Order> byUser_id = orderRepository.queryAllByUser_Id(id, pageable);
        if (byUser_id.getTotalElements() == 0) {
            throw new LocalException("no order made by this user", HttpStatus.NOT_FOUND);
        }

        if (page > byUser_id.getTotalPages()) {
            throw new LocalException("this page doesn't exist", HttpStatus.BAD_REQUEST);
        }

        return pagedResourcesAssembler.toModel(byUser_id, orderAssembler);
    }

    @Override
    public void createUser() {
        User user = new User();
        userRepository.save(user);
    }

    @Override
    public OrderDto createOrder(Long id, Long giftCertificateId) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new LocalException("No user found by provided id", HttpStatus.NOT_FOUND));
        GiftCertificate giftCertificateById =
                giftCertificateRepository.findById(giftCertificateId).orElseThrow(() ->
                        new LocalException("no gift certificate found by this id", HttpStatus.NOT_FOUND));
        Double cost = giftCertificateById.getPrice();

        Order order = new Order();

        order.setUser(user);
        order.setCost(cost);
        order.setGiftCertificate(giftCertificateById);

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
