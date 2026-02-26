package com.deliverytech.delivery_api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados de resposta de um restaurante")
public class RestaurantResponseDTO {
    @Schema(description = "ID único do restaurante", example = "1")
    private Long id;
    
    @Schema(description = "Nome do restaurante", example = "Pizzaria do Zé")
    private String name;
    
    @Schema(description = "Categoria do restaurante", example = "Pizza")
    private String category;
    
    @Schema(description = "Endereço do restaurante", example = "Av. Paulista, 1000")
    private String address; 
    
    @Schema(description = "Telefone do restaurante", example = "11987654321")
    private String phoneNumber;
    
    @Schema(description = "Avaliação do restaurante", example = "4.5")
    private String rating;
    
    @Schema(description = "Taxa de entrega", example = "5.00")
    private String deliveryFee;
    
    @Schema(description = "Indica se o restaurante está ativo", example = "true")
    private boolean active;
}
