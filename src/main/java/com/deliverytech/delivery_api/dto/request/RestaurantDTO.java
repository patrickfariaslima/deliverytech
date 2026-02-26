package com.deliverytech.delivery_api.dto.request;

import java.math.BigDecimal;

import com.deliverytech.delivery_api.validation.ValidCEP;
import com.deliverytech.delivery_api.validation.ValidCategory;
import com.deliverytech.delivery_api.validation.ValidTelephone;
import com.deliverytech.delivery_api.validation.ValidWorkingHours;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para cadastro ou atualização de restaurante")
public class RestaurantDTO {
    @Schema(
        description = "Nome do restaurante",
        example = "Pizzaria do Zé",
        requiredMode = RequiredMode.REQUIRED,
        minLength = 1,
        maxLength = 255
    )
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Schema(
        description = "Categoria/tipo de culinária do restaurante",
        example = "Pizza",
        requiredMode = RequiredMode.REQUIRED,
        allowableValues = {"Pizza", "Hamburguer", "Japonesa", "Italiana", "Vegetariana", "Sobremesas"}
    )
    @NotBlank(message = "Category cannot be blank")
    @ValidCategory
    private String category;

    @Schema(
        description = "Endereço completo do restaurante",
        example = "Av. Paulista, 1000 - Bela Vista, São Paulo - SP",
        requiredMode = RequiredMode.REQUIRED,
        maxLength = 255
    )
    @NotBlank(message = "Address cannot be blank")
    @Size(max= 255, message = "Address must be at most 255 characters long")
    private String address;

    @Schema(
        description = "Telefone de contato do restaurante (formato: DDNNNNNNNNN)",
        example = "11987654321",
        requiredMode = RequiredMode.REQUIRED,
        pattern = "^[1-9]{2}(?:9[0-9]{8}|[2-5][0-9]{7})$"
    )
    @NotBlank(message = "Phone number cannot be blank")
    @ValidTelephone
    private String phoneNumber;

    @Schema(
        description = "Taxa de entrega padrão do restaurante",
        example = "5.00",
        requiredMode = RequiredMode.REQUIRED,
        minimum = "0.00"
    )
    @NotNull(message = "Delivery fee cannot be null")
    @DecimalMin(value = "0.00", message = "Delivery fee must be >= 0")
    private BigDecimal deliveryFee;

    @Schema(
        description = "Horário de funcionamento do restaurante (formato: HH:mm-HH:mm)",
        example = "08:00-22:00"
    )
    @ValidWorkingHours
    private String workingHours;

    @Schema(
        description = "Tempo médio de entrega em minutos",
        example = "30",
        minimum = "0",
        maximum = "120"
    )
    @Min(value = 10, message = "Tempo de entrega mínimo é de 10 minutos")
    @Max(value = 120, message = "Tempo de entrega máximo é de 120 minutos")
    private Integer deliveryTimeMinutes;

    @Schema(
        description = "CEP do restaurante (formato: 00000-000)",
        example = "12345-678"
    )
    @ValidCEP
    private String cep;
}
