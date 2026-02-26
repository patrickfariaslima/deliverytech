package com.deliverytech.delivery_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.deliverytech.delivery_api.enums.OrdersStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderReportDTO implements OrderReportProjection {
    private String orderNumber;
    private BigDecimal total;
    private OrdersStatus status;
    private LocalDateTime orderDate;
    private String clientName;
    private String restaurantName;
}
