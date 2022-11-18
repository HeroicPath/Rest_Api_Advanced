package com.epam.esm.Rest_Api_Advanced.controller;

import com.epam.esm.Rest_Api_Advanced.dto.OrderDto;
import com.epam.esm.Rest_Api_Advanced.exception.LocalException;
import com.epam.esm.Rest_Api_Advanced.hateoas.assembler.OrderAssembler;
import com.epam.esm.Rest_Api_Advanced.mapper.OrderMapper;
import com.epam.esm.Rest_Api_Advanced.model.Order;
import com.epam.esm.Rest_Api_Advanced.service.OrderService;
import com.epam.esm.Rest_Api_Advanced.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

@RestController @AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final OrderAssembler orderAssembler;
    private final PagedResourcesAssembler<Order> pagedResourcesAssembler;

    /**
     * <p>
     * Returns the PagedModel representation of Orders of the specified User
     * </p>
     *
     * @param id id of the specified User
     * @param page number of the page
     * @param size size of the page
     * @return The PagedModel representation of the requested Orders
     */
    @GetMapping(value = "/{id}", params = {"page", "size"})
    public PagedModel<OrderDto> getUserOrders(@PathVariable Long id,
                                              @RequestParam int page,
                                              @RequestParam int size,
                                              UriComponentsBuilder uriComponentsBuilder,
                                              HttpServletResponse httpServletResponse){
        Page<Order> ordersById = userService.getOrdersById(id, page, size);
        if (page > ordersById.getTotalPages()) {
            throw new LocalException("this page doesn't exist", HttpStatus.BAD_REQUEST);
        }

        return pagedResourcesAssembler.toModel(ordersById, orderAssembler);
    }

    /**
     * <p>
     * Returns the PagedModel representation of Orders
     * </p>
     *
     * @param page number of the page
     * @param size size of the page
     * @return The Page representation of the requested Orders
     */
    @GetMapping(params = {"page", "size"})
    public PagedModel<OrderDto> getAll(@RequestParam int page,
                                       @RequestParam int size){
        Page<Order> orders = orderService.getAll(page, size);
        if (page > orders.getTotalPages() - 1) {
            throw new LocalException("this page doesn't exist", HttpStatus.BAD_REQUEST);
        }

        return pagedResourcesAssembler.toModel(orders, orderAssembler);
    }

    /**
     * <p>
     * Deletes the specified Order by Id
     * </p>
     *
     * @param id the id of the Order
     */
    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable Long id) {
        return orderMapper.toDto(orderService.getById(id));
    }
}
