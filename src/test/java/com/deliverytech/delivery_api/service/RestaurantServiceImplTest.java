package com.deliverytech.delivery_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.deliverytech.delivery_api.dto.response.ProductResponseDTO;
import com.deliverytech.delivery_api.exceptions.EntityNotFoundException;
import com.deliverytech.delivery_api.model.Restaurant;
import com.deliverytech.delivery_api.repository.RestaurantRepository;
import com.deliverytech.delivery_api.service.api.ProductService;
import com.deliverytech.delivery_api.service.impl.RestaurantServiceImpl;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ProductService productService;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Test
    void getRestaurantById_shouldThrowWhenNotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> restaurantService.getRestaurantById(1L));
    }

    @Test
    void calculateDeliveryFee_shouldAddFeeBasedOnCep() {
        Restaurant restaurant = new Restaurant();
        restaurant.setDeliveryFee(new BigDecimal("5.00"));

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        BigDecimal fee = restaurantService.calculateDeliveryFee(1L, "12345-678");

        assertEquals(new BigDecimal("7.00"), fee);
    }

    @Test
    void getProductsByRestaurant_shouldDelegateToProductService() {
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(productService.getProductsByRestaurant(1L)).thenReturn(List.of(new ProductResponseDTO()));

        List<ProductResponseDTO> result = restaurantService.getProductsByRestaurant(1L);

        assertEquals(1, result.size());
        verify(productService).getProductsByRestaurant(1L);
    }
}