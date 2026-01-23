package com.deliverytech.delivery_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.model.OrderedItem;
import com.deliverytech.delivery_api.service.OrderedItemService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/itens-pedidos")
public class OrderedItemController {
    private final OrderedItemService orderedItemService;

    public OrderedItemController(OrderedItemService orderedItemService) {
        this.orderedItemService = orderedItemService;
    }

    @GetMapping("/pedidos/{orderId}")
    public ResponseEntity<List<OrderedItem>> findByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.status(200).body(orderedItemService.findByOrderId(orderId));
    }

    @GetMapping("/itens/{productId}")
    public ResponseEntity<List<OrderedItem>> findByProductId(@PathVariable Long productId) {
        return ResponseEntity.status(200).body(orderedItemService.findByProductId(productId));
    }
    
    
}
