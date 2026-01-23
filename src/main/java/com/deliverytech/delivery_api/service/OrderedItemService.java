package com.deliverytech.delivery_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.model.OrderedItem;
import com.deliverytech.delivery_api.repository.OrderedItemRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderedItemService {
    private final OrderedItemRepository orderedItemRepository;

    public OrderedItemService(OrderedItemRepository orderedItemRepository) {
        this.orderedItemRepository = orderedItemRepository;
    }

    public List<OrderedItem> findByOrderId(Long orderId) {
        return orderedItemRepository.findByOrderId(orderId);
    }

    public List<OrderedItem> findByProductId(Long productId) {
        return orderedItemRepository.findByProductId(productId);
    }
}
