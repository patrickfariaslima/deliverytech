package com.deliverytech.delivery_api.dto.response;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados de resposta de um item do pedido")
public class OrderedItemResponseDTO {
    @Schema(description = "ID do produto", example = "1")
    private Long productId;
    
    @Schema(description = "Nome do produto", example = "Pizza Margherita")
    private String productName;
    
    @Schema(description = "Quantidade do produto", example = "2")
    private Integer quantity;
    
    @Schema(description = "Preço unitário do item", example = "39.90")
    private BigDecimal itemPrice;
    
    @Schema(description = "Valor total do item (preço x quantidade)", example = "79.80")
    private BigDecimal total;
}
