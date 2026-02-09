package com.deliverytech.delivery_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.deliverytech.delivery_api.enums.OrdersStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDTO {
    private Long id;
    private String orderNumber;
    private OrdersStatus status;
    private BigDecimal total;
    private BigDecimal deliveryFee;
    private LocalDateTime orderDate;

    private ClientResponseDTO client;
    private RestaurantResponseDTO restaurant;

    private List<OrderedItemResponseDTO> items;
}
