package com.deliverytech.delivery_api.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados de resposta de um cliente")
public class ClientResponseDTO {
    
    @Schema(description = "ID único do cliente", example = "1")
    private Long id;
    
    @Schema(description = "Nome completo do cliente", example = "João Silva")
    private String name;

    @Schema(description = "Email do cliente", example = "joao.silva@example.com")
    private String email;

    @Schema(description = "Telefone do cliente", example = "11987654321")
    private String phoneNumber;

    @Schema(description = "Endereço do cliente", example = "Rua das Flores, 123")
    private String address;

    @Schema(description = "Indica se o cliente está ativo", example = "true")
    private boolean active;

    @Schema(description = "Data e hora do cadastro", example = "2024-01-15T10:30:00")
    private LocalDateTime registeredAt;
}
