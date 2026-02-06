package com.deliverytech.delivery_api.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product description is required")
    @Size(min= 5, message = "Description must be at least 5 characters long")
    private String description;

    @NotBlank(message = "Product category is required")
    private String category;

    @Positive(message = "Price must be a positive value")
    @NotBlank(message = "Product price is required")
    private BigDecimal price;
}
