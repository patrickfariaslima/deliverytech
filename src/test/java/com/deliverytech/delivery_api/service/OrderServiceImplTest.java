package com.deliverytech.delivery_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.deliverytech.delivery_api.dto.request.OrderDTO;
import com.deliverytech.delivery_api.dto.request.OrderedItemDTO;
import com.deliverytech.delivery_api.enums.OrdersStatus;
import com.deliverytech.delivery_api.exceptions.BusinessException;
import com.deliverytech.delivery_api.exceptions.EntityNotFoundException;
import com.deliverytech.delivery_api.model.Client;
import com.deliverytech.delivery_api.model.Order;
import com.deliverytech.delivery_api.model.Product;
import com.deliverytech.delivery_api.model.Restaurant;
import com.deliverytech.delivery_api.repository.ClientRepository;
import com.deliverytech.delivery_api.repository.OrderRepository;
import com.deliverytech.delivery_api.repository.OrderedItemRepository;
import com.deliverytech.delivery_api.repository.ProductRepository;
import com.deliverytech.delivery_api.repository.RestaurantRepository;
import com.deliverytech.delivery_api.service.impl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock private OrderRepository orderRepository;
    @Mock private ClientRepository clientRepository;
    @Mock private RestaurantRepository restaurantRepository;
    @Mock private OrderedItemRepository orderedItemRepository;
    @Mock private ProductRepository productRepository;
    @Mock private ModelMapper mapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createOrder_shouldThrowWhenClientInactive() {
        OrderDTO dto = new OrderDTO();
        dto.setClientId(1L);
        dto.setRestaurantId(1L);
        dto.setDeliveryAddress("Rua A, 123");

        OrderedItemDTO item = new OrderedItemDTO();
        item.setProductId(1L);
        item.setQuantity(1);
        dto.setItems(List.of(item));

        Client client = new Client();
        client.setActive(false);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        assertThrows(BusinessException.class, () -> orderService.createOrder(dto));
    }

    @Test
    void createOrder_shouldThrowWhenProductUnavailable() {
        OrderDTO dto = new OrderDTO();
        dto.setClientId(1L);
        dto.setRestaurantId(1L);
        dto.setDeliveryAddress("Rua A, 123");

        OrderedItemDTO item = new OrderedItemDTO();
        item.setProductId(1L);
        item.setQuantity(1);
        dto.setItems(List.of(item));

        Client client = new Client();
        client.setActive(true);

        Restaurant restaurant = new Restaurant();
        restaurant.setActive(true);

        Product product = new Product();
        product.setAvailable(false);
        product.setPrice(new BigDecimal("10.00"));
        product.setRestaurant(restaurant);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(BusinessException.class, () -> orderService.createOrder(dto));
    }

    @Test
    void updateOrderStatus_shouldThrowOnInvalidTransition() {
        Order order = new Order();
        order.setStatus(OrdersStatus.DELIVERED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(BusinessException.class, () -> orderService.updateOrderStatus(1L, OrdersStatus.CONFIRMED));
    }

    @Test
    void cancelOrder_shouldThrowWhenNotPending() {
        Order order = new Order();
        order.setStatus(OrdersStatus.CONFIRMED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(BusinessException.class, () -> orderService.cancelOrder(1L));
    }

    @Test
    void calculateOrderTotal_shouldThrowWhenProductNotFound() {
        OrderedItemDTO item = new OrderedItemDTO();
        item.setProductId(1L);
        item.setQuantity(1);

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.calculateOrderTotal(List.of(item)));
    }
}