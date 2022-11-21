package com.epam.esm.Rest_Api_Advanced.repository;

import com.epam.esm.Rest_Api_Advanced.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByUser_Id(Long id, Pageable pageable);
}
