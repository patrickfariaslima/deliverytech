package com.deliverytech.delivery_api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.deliverytech.delivery_api.dto.response.RestaurantResponseDTO;
import com.deliverytech.delivery_api.dto.response.ProductResponseDTO;
import com.deliverytech.delivery_api.service.api.RestaurantService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    void createRestaurant_shouldReturn201() throws Exception {
        when(restaurantService.createRestaurant(org.mockito.ArgumentMatchers.any()))
            .thenReturn(new RestaurantResponseDTO());

        mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"R\",\"category\":\"Cat\",\"address\":\"Rua A\",\"phoneNumber\":\"11999999999\",\"deliveryFee\":2.00}"))
            .andExpect(status().isCreated());
    }

    @Test
    void getRestaurantById_shouldReturn200() throws Exception {
        when(restaurantService.getRestaurantById(1L)).thenReturn(new RestaurantResponseDTO());

        mockMvc.perform(get("/api/restaurantes/1"))
            .andExpect(status().isOk());
    }

    @Test
    void calculateDeliveryFee_shouldReturn200() throws Exception {
        when(restaurantService.calculateDeliveryFee(1L, "12345-678")).thenReturn(new BigDecimal("5.00"));

        mockMvc.perform(get("/api/restaurantes/1/taxa-entrega/12345-678"))
            .andExpect(status().isOk());
    }

    @Test
    void getProductsByRestaurant_shouldReturn200() throws Exception {
        when(restaurantService.getProductsByRestaurant(1L)).thenReturn(List.of(new ProductResponseDTO()));

        mockMvc.perform(get("/api/restaurantes/1/produtos"))
            .andExpect(status().isOk());
    }
}