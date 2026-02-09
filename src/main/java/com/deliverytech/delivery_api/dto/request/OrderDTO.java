package com.deliverytech.delivery_api.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;

    @Valid
    @NotEmpty(message = "At least one item is required")
    private List<OrderedItemDTO> items;

    @NotBlank(message = "Delivery Address is required")
    @Size(min = 5, message = "Delivery address must have at least 5 characters long")
    private String deliveryAddress;
}
