package com.epam.esm.Rest_Api_Advanced.controller;

import com.epam.esm.Rest_Api_Advanced.dto.OrderDto;
import com.epam.esm.Rest_Api_Advanced.exception.LocalException;
import com.epam.esm.Rest_Api_Advanced.mapper.OrderMapper;
import com.epam.esm.Rest_Api_Advanced.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users") @AllArgsConstructor
public class UserController {

    private final OrderMapper orderMapper;
    private final UserService userService;

    /**
     * <p>
     * Creates a new User
     * </p>
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity createUser() {
        userService.createUser();
        return ResponseEntity.ok("New user has been created!");
    }

    /**
     * <p>
     * Places Order for the specified User
     * </p>
     *  @param id the User that places Order
     * @param giftCertificateId the Id of Gift Certificate that is being purchased
     */
    @PostMapping(value = "/{id}/order", params = "giftCertificateId")
    public OrderDto placeOrder(@PathVariable Long id,
                               @RequestParam Long giftCertificateId) {
        return orderMapper.toDto(userService.createOrder(id, giftCertificateId));
    }
}
