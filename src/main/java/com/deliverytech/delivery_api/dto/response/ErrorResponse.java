package com.deliverytech.delivery_api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta padrão para erros da API")
public class ErrorResponse {
    
    @Schema(description = "Indica falha na operação", example = "false")
    private Boolean success = false;
    
    @Schema(description = "Detalhes do erro ocorrido")
    private ErrorDetail error;
    
    @Schema(description = "Timestamp do erro", example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Detalhes específicos do erro")
    public static class ErrorDetail {
        
        @Schema(description = "Código identificador do erro", example = "ENTITY_NOT_FOUND")
        private String code;
        
        @Schema(description = "Mensagem descritiva do erro", example = "Restaurante não encontrado")
        private String message;
        
        @Schema(description = "Detalhes adicionais sobre o erro", example = "Nenhum restaurante encontrado com ID: 999")
        private String details;
    }
    
    public ErrorResponse(String code, String message, String details) {
        this.success = false;
        this.error = new ErrorDetail(code, message, details);
        this.timestamp = LocalDateTime.now();
    }
    
    public static ErrorResponse of(String code, String message, String details) {
        return new ErrorResponse(code, message, details);
    }
}