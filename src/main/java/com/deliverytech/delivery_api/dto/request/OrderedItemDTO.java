package com.deliverytech.delivery_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Item individual de um pedido")
public class OrderedItemDTO {
    
    @Schema(
        description = "ID do produto",
        example = "1",
        requiredMode = RequiredMode.REQUIRED
    )
    @NotNull(message = "Product ID is required")
    private Long productId;

    @Schema(
        description = "Quantidade do produto",
        example = "2",
        requiredMode = RequiredMode.REQUIRED,
        minimum = "1"
    )
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity;
}
