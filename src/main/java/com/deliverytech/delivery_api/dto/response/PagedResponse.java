package com.deliverytech.delivery_api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta paginada para listagens de recursos")
public class PagedResponse<T> {
    
    @Schema(description = "Lista de itens da página atual")
    private List<T> content;
    
    @Schema(description = "Informações sobre a paginação")
    private PageInfo page;
    
    @Schema(description = "Links para navegação entre páginas")
    private Map<String, String> links;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Metadados da paginação")
    public static class PageInfo {
        
        @Schema(description = "Número da página atual (inicia em 0)", example = "0")
        private Integer number;
        
        @Schema(description = "Quantidade de itens por página", example = "10")
        private Integer size;
        
        @Schema(description = "Total de elementos em todas as páginas", example = "50")
        private Long totalElements;
        
        @Schema(description = "Total de páginas disponíveis", example = "5")
        private Integer totalPages;
        
        @Schema(description = "Indica se é a primeira página", example = "true")
        private Boolean first;
        
        @Schema(description = "Indica se é a última página", example = "false")
        private Boolean last;
    }
}