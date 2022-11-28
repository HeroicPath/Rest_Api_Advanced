package com.epam.esm.RestApiAdvanced.service;

import com.epam.esm.RestApiAdvanced.dto.OrderDto;
import org.springframework.hateoas.PagedModel;

public interface UserService {

    /**
     * retrieves the list of orders of specified User
     * @param id id of the User
     * @return list of orders of specified User
     */
    PagedModel<OrderDto> getOrdersById (Long id, int page, int size);

    /**
     * <p>
     * Creates a new User
     * </p>
     */
    void createUser();

    /**
     * <p>
     * Places Order for the specified User
     * </p>
     *  @param id the User that places Order
     * @param giftCertificateId the Id of Gift Certificate that is being purchased
     * @return Dto representation of the newly created Order
     */
    OrderDto createOrder(Long id, Long giftCertificateId);

    /**
     * <p>
     * Deletes a User by id
     * </p>
     *  @param id the User that needs to be deleted
     */
    void deleteById(Long id);
}
