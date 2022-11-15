package Rest_Api_Advanced.service.impl;

import Rest_Api_Advanced.exception.LocalException;
import Rest_Api_Advanced.model.Order;
import Rest_Api_Advanced.repository.OrderRepository;
import Rest_Api_Advanced.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Page<Order> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("creationTimeStamp"));
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
            new LocalException("order by this id is not found"
                    ,HttpStatus.NOT_FOUND));
    }
}
