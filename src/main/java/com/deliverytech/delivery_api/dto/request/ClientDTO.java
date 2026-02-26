package com.deliverytech.delivery_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para cadastro ou atualização de cliente")
public class ClientDTO {

    @Schema(
        description = "Nome completo do cliente",
        example = "João Silva",
        requiredMode = RequiredMode.REQUIRED,
        minLength = 1,
        maxLength = 255
    )
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Schema(
        description = "Endereço de email do cliente",
        example = "joao.silva@example.com",
        requiredMode = RequiredMode.REQUIRED,
        format = "email"
    )
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(
        description = "Número de telefone no formato DDNNNNNNNNN",
        example = "11987654321",
        requiredMode = RequiredMode.REQUIRED,
        pattern = "^[1-9]{2}(?:9[0-9]{8}|[2-5][0-9]{7})$"
    )
    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[1-9]{2}(?:9[0-9]{8}|[2-5][0-9]{7})$", message = "Invalid phone number format. Format: DDNNNNNNNNN")
    private String phoneNumber;

    @Schema(
        description = "Endereço completo do cliente",
        example = "Rua das Flores, 123, Centro",
        minLength = 5
    )
    @Size(min= 5, message = "Address must be at least 5 characters long")
    private String address;
}
