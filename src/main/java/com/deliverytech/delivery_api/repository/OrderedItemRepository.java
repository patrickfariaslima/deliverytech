package com.deliverytech.delivery_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.model.OrderedItem;

@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long>{
    List<OrderedItem> findByOrderId(Long orderId);

    List<OrderedItem> findByProductId(Long productId);
}
