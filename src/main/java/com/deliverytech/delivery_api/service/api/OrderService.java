package com.deliverytech.delivery_api.service.api;

import java.math.BigDecimal;
import java.util.List;

import com.deliverytech.delivery_api.dto.request.OrderDTO;
import com.deliverytech.delivery_api.dto.request.OrderedItemDTO;
import com.deliverytech.delivery_api.dto.response.OrderResponseDTO;
import com.deliverytech.delivery_api.dto.response.OrderSummaryResponseDTO;
import com.deliverytech.delivery_api.enums.OrdersStatus;

public interface OrderService {
    OrderResponseDTO createOrder(OrderDTO dto);
    OrderResponseDTO getOrderById(Long orderId);
    List<OrderSummaryResponseDTO> getOrdersByClient(Long clientId);
    OrderResponseDTO updateOrderStatus(Long orderId, OrdersStatus status);
    BigDecimal calculateOrderTotal(List<OrderedItemDTO> items);
    OrderResponseDTO cancelOrder(Long orderId);
}
