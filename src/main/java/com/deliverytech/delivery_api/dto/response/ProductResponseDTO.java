package com.deliverytech.delivery_api.dto.response;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados de resposta de um produto")
public class ProductResponseDTO {
    @Schema(description = "ID único do produto", example = "1")
    private Long id;
    
    @Schema(description = "Nome do produto", example = "Pizza Margherita")
    private String name;
    
    @Schema(description = "Descrição do produto", example = "Pizza tradicional com mussarela")
    private String description;
    
    @Schema(description = "Categoria do produto", example = "Pizza")
    private String category;
    
    @Schema(description = "Preço do produto", example = "39.90")
    private BigDecimal price;
    
    @Schema(description = "Indica se o produto está disponível", example = "true")
    private boolean available;
    
    @Schema(description = "ID do restaurante que oferece o produto", example = "1")
    private Long restaurantId;
}
