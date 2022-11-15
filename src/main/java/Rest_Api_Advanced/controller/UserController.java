package Rest_Api_Advanced.controller;

import Rest_Api_Advanced.dto.OrderDto;
import Rest_Api_Advanced.hateoas.event.PageRetrievedEvent;
import Rest_Api_Advanced.exception.LocalException;
import Rest_Api_Advanced.mapper.OrderMapper;
import Rest_Api_Advanced.model.User;
import Rest_Api_Advanced.service.UserService;
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
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * <p>
     * Returns the Page representation of Orders of the specified User
     * </p>
     *
     * @param id id of the specified User
     * @param page number of the page
     * @param size size of the page
     * @return The Page representation of the requested Orders
     */
    @GetMapping(value = "/{id}", params = {"page", "size"})
    public Page<OrderDto> getUserOrders(@PathVariable Long id,
                                        @RequestParam int page,
                                        @RequestParam int size,
                                        UriComponentsBuilder uriComponentsBuilder,
                                        HttpServletResponse httpServletResponse){
        Page<OrderDto> ordersById = userService.getOrdersById(id, page, size).map(orderMapper::toDto);
        if (page > ordersById.getTotalPages()) {
            throw new LocalException("this page doesn't exist", HttpStatus.BAD_REQUEST);
        }

        applicationEventPublisher.publishEvent(
                new PageRetrievedEvent<>(
                        User.class,
                        uriComponentsBuilder,
                        httpServletResponse,
                        page,
                        ordersById.getTotalPages(),
                        size));

        return ordersById;
    }

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
