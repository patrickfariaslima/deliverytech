package com.deliverytech.delivery_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.deliverytech.delivery_api.enums.OrdersStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados resumidos de resposta de um pedido")
public class OrderSummaryResponseDTO {
    @Schema(description = "ID único do pedido", example = "1")
    private Long id;
    
    @Schema(description = "Número do pedido", example = "PED1234567890")
    private String orderNumber;
    
    @Schema(description = "Status atual do pedido", example = "PENDING")
    private OrdersStatus status;
    
    @Schema(description = "Valor total do pedido", example = "89.90")
    private BigDecimal total;
    
    @Schema(description = "Data e hora do pedido", example = "2024-01-15T10:30:00")
    private LocalDateTime orderDate;
}
