package com.deliverytech.delivery_api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.deliverytech.delivery_api.dto.response.ProductResponseDTO;
import com.deliverytech.delivery_api.service.api.ProductService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void createProduct_shouldReturn201() throws Exception {
        when(productService.createProduct(org.mockito.ArgumentMatchers.any()))
            .thenReturn(new ProductResponseDTO());

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Pizza Margherita\",\"description\":\"Descricao\",\"category\":\"Pizza\",\"price\":10.0,\"restaurantId\":1}"))
            .andExpect(status().isCreated());
    }

    @Test
    void getProductById_shouldReturn200() throws Exception {
        when(productService.getProductById(1L)).thenReturn(new ProductResponseDTO());

        mockMvc.perform(get("/api/produtos/1"))
            .andExpect(status().isOk());
    }

    @Test
    void setAvailability_shouldReturn200() throws Exception {
        when(productService.setProductAvailability(1L, true)).thenReturn(new ProductResponseDTO());

        mockMvc.perform(patch("/api/produtos/1/disponibilidade?available=true"))
            .andExpect(status().isOk());
    }
}