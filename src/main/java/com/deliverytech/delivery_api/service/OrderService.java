package com.deliverytech.delivery_api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.enums.OrdersStatus;
import com.deliverytech.delivery_api.model.Client;
import com.deliverytech.delivery_api.model.Order;
import com.deliverytech.delivery_api.model.OrderedItem;
import com.deliverytech.delivery_api.model.Product;
import com.deliverytech.delivery_api.model.Restaurant;
import com.deliverytech.delivery_api.repository.ClientRepository;
import com.deliverytech.delivery_api.repository.OrderRepository;
import com.deliverytech.delivery_api.repository.OrderedItemRepository;
import com.deliverytech.delivery_api.repository.ProductRepository;
import com.deliverytech.delivery_api.repository.RestaurantRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderedItemRepository orderedItemRepository;

    @Autowired
    private ProductRepository productRepository;
    

    public OrderService(OrderRepository orderRepository, ClientRepository clientRepository,
            RestaurantRepository restaurantRepository, OrderedItemRepository orderedItemRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderedItemRepository = orderedItemRepository;
        this.productRepository = productRepository;
    }

    public Order createOrder(Long clientId, Long restaurantId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new IllegalArgumentException("Client not found"));

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        Order newOrder = new Order();
        newOrder.setClient(client);
        newOrder.setRestaurant(restaurant);
        newOrder.setStatus(OrdersStatus.PENDING);
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setTotal(BigDecimal.ZERO);

        return orderRepository.save(newOrder);
    }

    public Order updateStatus(Long orderId, OrdersStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.setStatus(status);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByClient(Long clientId) {
        return orderRepository.findByClientId(clientId);
    }

    public OrderedItem addItem(Long orderId, Long productId, Integer quantity) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));

        OrderedItem item = new OrderedItem();

        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setItemPrice(product.getPrice());

        BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        order.setTotal(total);

        orderedItemRepository.save(item);

        order.setTotal(order.getTotal().add(total));

        orderRepository.save(order);

        return item;
    }

    


}
