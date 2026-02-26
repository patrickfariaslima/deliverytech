package com.deliverytech.delivery_api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Wrapper padrão para respostas de sucesso da API")
public class ApiResponse<T> {
    
    @Schema(description = "Indica se a operação foi bem-sucedida", example = "true")
    private Boolean success;
    
    @Schema(description = "Dados retornados pela operação")
    private T data;
    
    @Schema(description = "Mensagem descritiva sobre a operação", example = "Operação realizada com sucesso")
    private String message;
    
    @Schema(description = "Timestamp da resposta", example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp;
    
    public ApiResponse(Boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
    
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, message, LocalDateTime.now());
    }
    
    public static <T> ApiResponse<T> success(T data) {
        return success(data, "Operação realizada com sucesso");
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, null, message, LocalDateTime.now());
    }
}