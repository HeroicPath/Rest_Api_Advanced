package com.epam.esm.RestApiAdvanced.controller;

import com.epam.esm.RestApiAdvanced.dto.OrderDto;
import com.epam.esm.RestApiAdvanced.service.OrderService;
import com.epam.esm.RestApiAdvanced.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

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
    @GetMapping(value = "/user", params = {"id", "page", "size"})
    public PagedModel<OrderDto> getOrdersByUserId(@RequestParam @Valid Long id,
                                                  @RequestParam @Valid int page,
                                                  @RequestParam @Valid int size){
        return userService.getOrdersById(id, page, size);
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
    public PagedModel<OrderDto> getAll(@RequestParam @Valid int page,
                                       @RequestParam @Valid int size){
        return orderService.getAll(page, size);
    }

    /**
     * <p>
     * Deletes the specified Order by Id
     * </p>
     *
     * @param id the id of the Order
     */
    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable @Valid Long id) {
        return orderService.getById(id);
    }

    /**
     * <p>
     * Deletes an Order by id
     * </p>
     *  @param id the id of Order that needs to be deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnOrderById(@PathVariable @Valid Long id){
        orderService.deleteById(id);
        return ResponseEntity.ok("An order by id " + id + " was deleted!");
    }
}
