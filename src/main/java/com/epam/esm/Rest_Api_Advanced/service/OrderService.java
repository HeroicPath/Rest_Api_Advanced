package com.epam.esm.Rest_Api_Advanced.service;

import com.epam.esm.Rest_Api_Advanced.model.Order;
import org.springframework.data.domain.Page;

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
    Page<Order> getAll(int page, int size);

    /**
     * returns the Order by id
     * @param id id of the requested entity
     * @return the requested entity
     */
    Order getById(Long id);
}
