package com.deliverytech.delivery_api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.deliverytech.delivery_api.dto.response.OrderResponseDTO;
import com.deliverytech.delivery_api.service.api.OrderService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    void createOrder_shouldReturn201() throws Exception {
        when(orderService.createOrder(org.mockito.ArgumentMatchers.any()))
            .thenReturn(new OrderResponseDTO());

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"clientId\":1,\"restaurantId\":1,\"deliveryAddress\":\"Rua A\",\"items\":[{\"productId\":1,\"quantity\":1}]}"))
            .andExpect(status().isCreated());
    }

    @Test
    void getOrderById_shouldReturn200() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(new OrderResponseDTO());

        mockMvc.perform(get("/api/pedidos/1"))
            .andExpect(status().isOk());
    }

    @Test
    void cancelOrder_shouldReturn200() throws Exception {
        when(orderService.cancelOrder(1L)).thenReturn(new OrderResponseDTO());

        mockMvc.perform(delete("/api/pedidos/1"))
            .andExpect(status().isOk());
    }

    @Test
    void calculateTotal_shouldReturn200() throws Exception {
        when(orderService.calculateOrderTotal(org.mockito.ArgumentMatchers.anyList()))
            .thenReturn(new BigDecimal("10.00"));

        mockMvc.perform(post("/api/pedidos/calcular")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"productId\":1,\"quantity\":1}]"))
            .andExpect(status().isOk());
    }
}