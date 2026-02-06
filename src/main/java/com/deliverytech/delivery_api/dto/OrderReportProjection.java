package com.deliverytech.delivery_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.deliverytech.delivery_api.enums.OrdersStatus;

public interface OrderReportProjection {
    String getOrderNumber();
    BigDecimal getTotal();
    OrdersStatus getStatus();
    LocalDateTime getOrderDate();
    String getClientName();
    String getRestaurantName();
}
