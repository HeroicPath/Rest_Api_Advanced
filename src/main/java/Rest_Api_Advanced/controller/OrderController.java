package Rest_Api_Advanced.controller;

import Rest_Api_Advanced.dto.OrderDto;
import Rest_Api_Advanced.hateoas.event.PageRetrievedEvent;
import Rest_Api_Advanced.exception.LocalException;
import Rest_Api_Advanced.mapper.OrderMapper;
import Rest_Api_Advanced.model.Order;
import Rest_Api_Advanced.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

@RestController @AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * <p>
     * Returns the Page representation of Orders
     * </p>
     *
     * @param page number of the page
     * @param size size of the page
     * @return The Page representation of the requested Orders
     */
    @GetMapping(params = {"page", "size"})
    public Page<OrderDto> getAll(@RequestParam int page,
                                 @RequestParam int size,
                                 UriComponentsBuilder uriComponentsBuilder,
                                 HttpServletResponse httpServletResponse){
        Page<OrderDto> orders = orderService.getAll(page, size).map(orderMapper::toDto);
        if (page > orders.getTotalPages() - 1) {
            throw new LocalException("this page doesn't exist", HttpStatus.BAD_REQUEST);
        }

        applicationEventPublisher.publishEvent(
                new PageRetrievedEvent<>(
                        Order.class,
                        uriComponentsBuilder,
                        httpServletResponse,
                        page,
                        orders.getTotalPages(),
                        size));

        return orders;
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
