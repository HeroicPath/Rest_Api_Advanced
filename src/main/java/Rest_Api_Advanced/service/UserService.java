package Rest_Api_Advanced.service;

import Rest_Api_Advanced.model.Order;
import org.springframework.data.domain.Page;

public interface UserService {

    /**
     * retrieves the list of orders of specified User
     * @param id id of the User
     * @return list of orders of specified User
     */
    Page<Order> getOrdersById (Long id, int page, int size);

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
     *
     * @param id the User that places Order
     * @param giftCertificateId the Id of Gift Certificate that is being purchased
     */
    Order createOrder(Long id, Long giftCertificateId);
}
