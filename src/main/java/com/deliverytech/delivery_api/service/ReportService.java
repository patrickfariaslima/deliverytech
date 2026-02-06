package com.deliverytech.delivery_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.dto.ClientRankingProjection;
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
}
