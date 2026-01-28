package com.deliverytech.delivery_api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.dto.TotalSalesByRestaurantDTO;
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
    
    List<Order> findByRestaurantId(Long restaurantId);

    @Query("""
            select new com.deliverytech.delivery_api.dto.TotalSalesByRestaurantDTO(
                    r.name,
                    coalesce(sum(ip.subtotal), 0)
                )
                from Order o
                join o.restaurant r
                join o.items ip
                group by r.name
    """)
    List<TotalSalesByRestaurantDTO> TotalSalesByRestaurant();

    @Query(value="""
        SELECT c.name AS client, COUNT(p.id) AS total_orders
        FROM orders o 
        JOIN clients c ON c.id = p.client_id
        GROUP BY c.name
        ORDER BY total_orders DESC
    """, nativeQuery = true )
    List<Object[]> rankingClients();
}
