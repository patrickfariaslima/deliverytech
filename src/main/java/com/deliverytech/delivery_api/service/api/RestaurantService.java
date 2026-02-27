package com.deliverytech.delivery_api.service.api;

import java.math.BigDecimal;
import java.util.List;

import com.deliverytech.delivery_api.dto.request.RestaurantDTO;
import com.deliverytech.delivery_api.dto.response.ProductResponseDTO;
import com.deliverytech.delivery_api.dto.response.RestaurantResponseDTO;

public interface RestaurantService {
    RestaurantResponseDTO createRestaurant(RestaurantDTO dto);
    RestaurantResponseDTO getRestaurantById(Long restaurantId);
    List<RestaurantResponseDTO> getRestaurantsByCategory(String category);
    List<RestaurantResponseDTO> getActiveRestaurants();
    RestaurantResponseDTO updateRestaurant(Long restaurantId, RestaurantDTO dto);
    BigDecimal calculateDeliveryFee(Long restaurantId, String cep);
    List<ProductResponseDTO> getProductsByRestaurant(Long restaurantId);
    void toggleRestaurantStatus(Long restaurantId);
    List<RestaurantResponseDTO> getNearbyRestaurants(String cep);
    boolean isOwner(Long restaurantId);
}
