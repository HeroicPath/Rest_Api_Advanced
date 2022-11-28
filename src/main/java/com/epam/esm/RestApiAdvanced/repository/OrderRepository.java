package com.epam.esm.RestApiAdvanced.repository;

import com.epam.esm.RestApiAdvanced.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser_Id(Long id);

    Page<Order> queryAllByUser_Id(Long id, Pageable pageable);
}
