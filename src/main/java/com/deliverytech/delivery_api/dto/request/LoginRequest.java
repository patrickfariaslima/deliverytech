package com.deliverytech.delivery_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para login de usuário")
public class LoginRequest {
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
        format = "password"
    )
    @NotBlank(message = "Password is required")
    private String password;
}
