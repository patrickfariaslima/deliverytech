package com.deliverytech.delivery_api.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para criação de um pedido")
public class OrderDTO {
    
    @Schema(
        description = "ID do cliente que está fazendo o pedido",
        example = "1",
        requiredMode = RequiredMode.REQUIRED
    )
    @NotNull(message = "Client ID is required")
    private Long clientId;

    @Schema(
        description = "ID do restaurante onde o pedido será feito",
        example = "1",
        requiredMode = RequiredMode.REQUIRED
    )
    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;

    @Schema(
        description = "Lista de itens do pedido",
        requiredMode = RequiredMode.REQUIRED,
        minLength = 1
    )
    @Valid
    @NotEmpty(message = "At least one item is required")
    private List<OrderedItemDTO> items;

    @Schema(
        description = "Endereço completo para entrega",
        example = "Rua das Palmeiras, 456, Jardim América",
        requiredMode = RequiredMode.REQUIRED,
        minLength = 5
    )
    @NotBlank(message = "Delivery Address is required")
    @Size(min = 5, message = "Delivery address must have at least 5 characters long")
    private String deliveryAddress;
}
