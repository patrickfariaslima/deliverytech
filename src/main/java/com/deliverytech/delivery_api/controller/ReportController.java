package com.deliverytech.delivery_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.dto.TotalSalesByRestaurantDTO;
import com.deliverytech.delivery_api.service.ReportService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/relatorio")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/total-vendas-por-restaurante")
    public List<TotalSalesByRestaurantDTO> totalSalesByRestaurant() {
        return reportService.totalSalesByRestaurant();
    }

    @GetMapping("/ranking-clientes")
    public List<Object[]> rankingClients() {
        return reportService.rankingClients();
    }
}
