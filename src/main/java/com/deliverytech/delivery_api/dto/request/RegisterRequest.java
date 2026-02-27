package com.deliverytech.delivery_api.dto.request;

import com.deliverytech.delivery_api.enums.UserRole;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para registro de novo usuário")
public class RegisterRequest {

    @Schema(
        description = "Nome completo do usuário",
        example = "João Silva",
        requiredMode = RequiredMode.REQUIRED,
        minLength = 3,
        maxLength = 100
    )
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Schema(
        description = "Email do usuário",
        example = "joao@example.com",
        requiredMode = RequiredMode.REQUIRED,
        format = "email"
    )
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(
        description = "Senha do usuário",
        example = "senha123",
        requiredMode = RequiredMode.REQUIRED,
        format = "password",
        minLength = 6
    )
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Schema(
        description = "Perfil do usuário (CLIENT, RESTAURANT, ADMIN)",
        example = "CLIENT",
        requiredMode = RequiredMode.REQUIRED,
        allowableValues = {"CLIENT", "RESTAURANT", "ADMIN"}
    )
    @NotNull(message = "Role is required")
    private UserRole role;
    
    @Schema(
        description = "ID do restaurante (obrigatório apenas para role RESTAURANT)",
        example = "1"
    )
    private Long restaurantId;
}
