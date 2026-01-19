package com.deliverytech.delivery_api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.enums.OrdersStatus;
import com.deliverytech.delivery_api.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientId(Long clientId);

    List<Order> findByStatus(OrdersStatus status);

    @Query("""
            SELECT o FROM Order o
            WHERE o.orderDate BETWEEN :startDate AND :endDate
    """)
    List<Order> findByDateTime(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
}
