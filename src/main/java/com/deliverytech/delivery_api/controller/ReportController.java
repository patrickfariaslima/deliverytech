package com.deliverytech.delivery_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.dto.ClientRankingProjection;
import com.deliverytech.delivery_api.dto.TotalSalesByRestaurantDTO;
import com.deliverytech.delivery_api.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/relatorio")
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
}
