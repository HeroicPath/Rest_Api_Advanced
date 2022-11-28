package com.epam.esm.RestApiAdvanced.service;

import com.epam.esm.RestApiAdvanced.dto.OrderDto;
import org.springframework.hateoas.PagedModel;

public interface OrderService {

    /**
     * <p>
     * returns the Page, comprised of Orders
     * </p>
     *
     * @param page page number
     * @param size page size
     * @return Page representation of Orders
     */
    PagedModel<OrderDto> getAll(int page, int size);

    /**
     * returns the Order by id
     * @param id id of the requested entity
     * @return the requested entity
     */
    OrderDto getById(Long id);

    /**
     * <p>
     * Deletes an Order by id
     * </p>
     *  @param id the id of Order that needs to be deleted
     */
    void deleteById(Long id);
}
