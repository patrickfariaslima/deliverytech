package com.deliverytech.delivery_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.enums.OrdersStatus;
import com.deliverytech.delivery_api.model.Order;
import com.deliverytech.delivery_api.service.OrderService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/pedidos")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestParam Long clientId, @RequestParam Long restaurantId) {
        return ResponseEntity.status(201).body(orderService.createOrder(clientId, restaurantId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestBody OrdersStatus status) {
        return ResponseEntity.status(200).body(orderService.updateStatus(id, status));
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<Order>> getOrdersByClient(@PathVariable Long id) {
        return ResponseEntity.status(200).body(orderService.getOrdersByClient(id));
    }
    
}
