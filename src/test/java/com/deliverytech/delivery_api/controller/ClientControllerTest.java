package com.deliverytech.delivery_api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.deliverytech.delivery_api.dto.response.ClientResponseDTO;
import com.deliverytech.delivery_api.dto.response.OrderSummaryResponseDTO;
import com.deliverytech.delivery_api.service.api.ClientService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService clientService;

    @Test
    void createClient_shouldReturn201() throws Exception {
        when(clientService.createClient(org.mockito.ArgumentMatchers.any()))
            .thenReturn(new ClientResponseDTO());

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"A\",\"email\":\"a@b.com\",\"phoneNumber\":\"11999999999\",\"address\":\"Rua A\"}"))
            .andExpect(status().isCreated());
    }

    @Test
    void getClientById_shouldReturn200() throws Exception {
        when(clientService.getClientById(1L)).thenReturn(new ClientResponseDTO());

        mockMvc.perform(get("/api/clientes/1"))
            .andExpect(status().isOk());
    }

    @Test
    void toggleClientStatus_shouldReturn204() throws Exception {
        mockMvc.perform(patch("/api/clientes/1/status"))
            .andExpect(status().isNoContent());
    }

    @Test
    void getOrdersByClient_shouldReturn200() throws Exception {
        when(clientService.getOrdersByClient(1L)).thenReturn(List.of(new OrderSummaryResponseDTO()));

        mockMvc.perform(get("/api/clientes/1/pedidos"))
            .andExpect(status().isOk());
    }
}