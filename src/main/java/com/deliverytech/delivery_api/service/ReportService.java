package com.deliverytech.delivery_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.dto.TotalSalesByRestaurantDTO;
import com.deliverytech.delivery_api.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final OrderRepository orderRepository;

    public List<TotalSalesByRestaurantDTO> totalSalesByRestaurant(){
        return orderRepository.TotalSalesByRestaurant();
    }

    public List<Object[]> rankingClients(){
        return orderRepository.rankingClients();
    }
}
