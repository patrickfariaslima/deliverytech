package com.deliverytech.delivery_api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta do login contendo token JWT e dados do usuário")
public class LoginResponse {
    @Schema(
        description = "Token JWT para autenticação",
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    private String token;
    
    @Schema(
        description = "Tipo do token",
        example = "Bearer",
        defaultValue = "Bearer"
    )
    @Builder.Default
    private String type = "Bearer";
    
    @Schema(
        description = "ID do usuário",
        example = "1"
    )
    private Long userId;
    
    @Schema(
        description = "Nome do usuário",
        example = "João Silva"
    )
    private String name;
    
    @Schema(
        description = "Email do usuário",
        example = "joao@example.com"
    )
    private String email;
    
    @Schema(
        description = "Perfil/Role do usuário",
        example = "CLIENT"
    )
    private String role;
    
    @Schema(
        description = "ID do restaurante (apenas para role RESTAURANT)",
        example = "1"
    )
    private Long restaurantId;
    
    @Schema(
        description = "Tempo de expiração do token em milissegundos",
        example = "86400000"
    )
    private Long expiresIn;
}
