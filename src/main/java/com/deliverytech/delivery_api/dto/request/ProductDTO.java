package com.deliverytech.delivery_api.dto.request;

import java.math.BigDecimal;

import com.deliverytech.delivery_api.validation.ValidCategory;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para cadastro ou atualização de produto")
public class ProductDTO {
    
    @Schema(
        description = "Nome do produto",
        example = "Pizza Margherita",
        requiredMode = RequiredMode.REQUIRED,
        minLength = 3,
        maxLength = 100
    )
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Schema(
        description = "Descrição detalhada do produto",
        example = "Pizza tradicional com molho de tomate, mussarela e manjericão fresco",
        minLength = 10,
        maxLength = 500
    )
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    @Schema(
        description = "Categoria do produto",
        example = "Pizza",
        requiredMode = RequiredMode.REQUIRED
    )
    @NotBlank(message = "Category is required")
    @ValidCategory
    private String category;

    @Schema(
        description = "Preço do produto",
        example = "39.90",
        requiredMode = RequiredMode.REQUIRED,
        minimum = "0.01",
        maximum = "500.00"
    )
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @DecimalMax(value = "500.00", message = "Price must be less than or equal to 500")
    private BigDecimal price;

    @Schema(
        description = "ID do restaurante ao qual o produto pertence",
        example = "1",
        requiredMode = RequiredMode.REQUIRED
    )
    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;
}