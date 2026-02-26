package com.deliverytech.delivery_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.dto.ClientRankingProjection;
import com.deliverytech.delivery_api.dto.OrderReportProjection;
import com.deliverytech.delivery_api.dto.TopProductProjection;
import com.deliverytech.delivery_api.dto.TotalSalesByRestaurantDTO;
import com.deliverytech.delivery_api.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
@Tag(name = "Relatórios", description = "Endpoints de relatórios")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/total-vendas-por-restaurante")
    @Operation(summary = "Total de vendas por restaurante")
    @ApiResponse(responseCode = "200", description = "Relatório gerado")
    public List<TotalSalesByRestaurantDTO> totalSalesByRestaurant() {
        return reportService.totalSalesByRestaurant();
    }

    @GetMapping("/ranking-clientes")
    @Operation(summary = "Ranking de clientes")
    @ApiResponse(responseCode = "200", description = "Relatório gerado")
    public List<ClientRankingProjection> rankingClients() {
        return reportService.rankingClients();
    }

    @GetMapping("/produtos-mais-vendidos")
    @Operation(
        summary = "Produtos mais vendidos",
        description = "Retorna os produtos mais vendidos ordenados por quantidade total de vendas"
    )
    @ApiResponse(responseCode = "200", description = "Lista de produtos mais vendidos")
    public List<TopProductProjection> getTopProducts(
        @Parameter(description = "Quantidade de produtos a retornar", example = "10")
        @RequestParam(defaultValue = "10") int limit
    ) {
        return reportService.getTopProducts(limit);
    }
    
    @GetMapping("/pedidos-por-periodo")
    @Operation(
        summary = "Relatório de pedidos por período",
        description = "Retorna estatísticas detalhadas de pedidos em um período específico"
    )
    @ApiResponse(responseCode = "200", description = "Relatório de pedidos gerado")
    public List<OrderReportProjection> getOrdersByPeriod(
        @Parameter(description = "Data inicial no formato yyyy-MM-dd", example = "2024-01-01", required = true)
        @RequestParam String startDate,
        @Parameter(description = "Data final no formato yyyy-MM-dd", example = "2024-12-31", required = true)
        @RequestParam String endDate
    ) {
        return reportService.getOrdersByPeriod(startDate, endDate);
    }
}
