package com.deliverytech.delivery_api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.dto.ClientRankingProjection;
import com.deliverytech.delivery_api.dto.OrderReportDTO;
import com.deliverytech.delivery_api.dto.OrderReportProjection;
import com.deliverytech.delivery_api.dto.TopProductProjection;
import com.deliverytech.delivery_api.dto.TotalSalesByRestaurantDTO;
import com.deliverytech.delivery_api.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final OrderRepository orderRepository;

    public List<TotalSalesByRestaurantDTO> totalSalesByRestaurant(){
        return orderRepository.totalSalesByRestaurant();
    }

    public List<ClientRankingProjection> rankingClients(){
        return orderRepository.rankingClients();
    }

    public List<TopProductProjection> getTopProducts(int limit) {
    List<TopProductProjection> products = orderRepository.topProducts();
    return products.stream().limit(limit).toList();
}

    public List<OrderReportProjection> getOrdersByPeriod(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
        return orderRepository.findByDateTime(start, end).stream()
            .<OrderReportProjection>map(o -> new OrderReportDTO(
                o.getOrderNumber(),
                o.getTotal(),
                o.getStatus(),
                o.getOrderDate(),
                o.getClient().getName(),
                o.getRestaurant().getName()
            ))
            .toList();
    }
}
