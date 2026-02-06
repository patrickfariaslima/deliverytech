package com.deliverytech.delivery_api.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.dto.ClientRankingProjection;
import com.deliverytech.delivery_api.dto.OrderReportProjection;
import com.deliverytech.delivery_api.dto.RevenueByCategoryProjection;
import com.deliverytech.delivery_api.dto.TopProductProjection;
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
                    coalesce(sum(ip.total), 0)
                )
                from Order o
                join o.restaurant r
                join o.items ip
                group by r.name
    """)
    List<TotalSalesByRestaurantDTO> totalSalesByRestaurant();

    List<Order> findTop10ByOrderByOrderDateDesc();

    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("""
            SELECT o FROM Order o
            WHERE o.total > :amount
    """)
    List<Order> findByTotalGreaterThan(@Param("amount") BigDecimal amount);

    @Query("""
            SELECT o FROM Order o
            WHERE o.status = :status
            AND o.orderDate BETWEEN :startDate AND :endDate
    """)
    List<Order> findReportByPeriodAndStatus(
        @Param("status") OrdersStatus status,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query("""
        SELECT o.orderNumber as orderNumber,
               o.total AS total,
               o.status AS status,
               o.orderDate AS orderDate,
               c.name AS clientName,
               r.name AS restaurantName
        FROM Order o
        JOIN o.client c
        JOIN o.restaurant r
        WHERE o.orderDate BETWEEN :startDate AND :endDate
        AND o.status = :status        
    """)
    List<OrderReportProjection> reportByPeriodAndStatus(
        @Param("status") OrdersStatus status,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query(value = """
        SELECT p.name AS productName,
            SUM(oi.quantity) AS totalOrders
        FROM ordered_items oi
        JOIN products p ON p.id = oi.product_id
        GROUP BY p.name
        ORDER BY totalOrders DESC
    """, nativeQuery = true)
    List<TopProductProjection> topProducts();

    @Query(value ="""
            SELECT p.category AS category,
                COALESCE(SUM(oi.total), 0) AS totalRevenue
            FROM ordered_items oi
            JOIN products p ON p.id = oi.product_id
            GROUP BY p.category
            ORDER BY totalRevenue DESC
    """, nativeQuery = true)
    List<RevenueByCategoryProjection> revenueByCategory();

    @Query(value ="""
            SELECT c.name AS clientName,
                COUNT(o.id) AS totalOrders
            FROM orders o
            JOIN clients c ON c.id = o.client_id
            GROUP BY c.name
            ORDER BY totalOrders DESC
            """, nativeQuery = true)
    List<ClientRankingProjection> rankingClients();
}
