package com.deliverytech.delivery_api.dto.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderedItemResponseDTO {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal itemPrice;
    private BigDecimal total;
}
