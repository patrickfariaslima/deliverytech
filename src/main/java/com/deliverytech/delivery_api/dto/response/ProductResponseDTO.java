package com.deliverytech.delivery_api.dto.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private boolean available;
    private Long restaurantId;
}
