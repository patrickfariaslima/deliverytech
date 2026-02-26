package com.deliverytech.delivery_api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta para erros de validação de dados")
public class ValidationErrorResponse {
    
    @Schema(description = "Indica falha na operação", example = "false")
    private Boolean success = false;
    
    @Schema(description = "Mensagem geral do erro", example = "Dados de entrada inválidos")
    private String message;
    
    @Schema(description = "Mapa de campos com seus respectivos erros de validação")
    private Map<String, String> errors;
    
    @Schema(description = "Timestamp do erro", example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp;
    
    public ValidationErrorResponse(String message, Map<String, String> errors) {
        this.success = false;
        this.message = message;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }
    
    public static ValidationErrorResponse of(String message, Map<String, String> errors) {
        return new ValidationErrorResponse(message, errors);
    }
}