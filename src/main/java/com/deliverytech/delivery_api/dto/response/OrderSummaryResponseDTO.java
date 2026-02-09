package com.deliverytech.delivery_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.deliverytech.delivery_api.enums.OrdersStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSummaryResponseDTO {
    private Long id;
    private String orderNumber;
    private OrdersStatus status;
    private BigDecimal total;
    private LocalDateTime orderDate;
}
