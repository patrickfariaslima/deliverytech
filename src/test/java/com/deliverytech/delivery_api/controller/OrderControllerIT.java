package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.request.*;
import com.deliverytech.delivery_api.enums.OrdersStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long clientId;
    private Long restaurantId;
    private Long productId;

    @BeforeEach
    public void setUp() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("Cliente Teste");
        clientDTO.setEmail("cliente@teste.com");
        clientDTO.setPhoneNumber("11987654321");
        clientDTO.setAddress("Rua do Cliente, 100");

        String clientResponse = mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        clientId = objectMapper.readTree(clientResponse).get("id").asLong();

        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName("Restaurante Teste Pedidos");
        restaurantDTO.setCategory("Pizza");
        restaurantDTO.setAddress("Rua do Restaurante, 200");
        restaurantDTO.setPhoneNumber("11987654322");
        restaurantDTO.setDeliveryFee(new BigDecimal("10.00"));

        String restaurantResponse = mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        restaurantId = objectMapper.readTree(restaurantResponse).get("id").asLong();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Pizza Teste");
        productDTO.setDescription("Pizza para testes");
        productDTO.setPrice(new BigDecimal("35.00"));
        productDTO.setCategory("Pizza");
        productDTO.setRestaurantId(restaurantId);

        String productResponse = mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        productId = objectMapper.readTree(productResponse).get("id").asLong();
    }

    @Test
    public void testCreateOrder_Success() throws Exception {
        OrderedItemDTO item = new OrderedItemDTO();
        item.setProductId(productId);
        item.setQuantity(2);

        List<OrderedItemDTO> items = new ArrayList<>();
        items.add(item);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setClientId(clientId);
        orderDTO.setRestaurantId(restaurantId);
        orderDTO.setItems(items);
        orderDTO.setDeliveryAddress("Rua de Entrega, 300");

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clientId").value(clientId))
                .andExpect(jsonPath("$.restaurantId").value(restaurantId))
                .andExpect(jsonPath("$.deliveryAddress").value("Rua de Entrega, 300"));
    }

    @Test
    public void testCreateOrder_ValidationError() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setClientId(clientId);
        orderDTO.setRestaurantId(restaurantId);

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetOrderById_Success() throws Exception {
        OrderedItemDTO item = new OrderedItemDTO();
        item.setProductId(productId);
        item.setQuantity(1);

        List<OrderedItemDTO> items = new ArrayList<>();
        items.add(item);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setClientId(clientId);
        orderDTO.setRestaurantId(restaurantId);
        orderDTO.setItems(items);
        orderDTO.setDeliveryAddress("Rua de Busca, 400");

        String response = mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/api/pedidos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.deliveryAddress").value("Rua de Busca, 400"));
    }

    @Test
    public void testGetOrderById_NotFound() throws Exception {
        mockMvc.perform(get("/api/pedidos/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateOrderStatus_Success() throws Exception {
        OrderedItemDTO item = new OrderedItemDTO();
        item.setProductId(productId);
        item.setQuantity(1);

        List<OrderedItemDTO> items = new ArrayList<>();
        items.add(item);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setClientId(clientId);
        orderDTO.setRestaurantId(restaurantId);
        orderDTO.setItems(items);
        orderDTO.setDeliveryAddress("Rua Status, 500");

        String response = mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(patch("/api/pedidos/" + id + "/status")
                .param("status", OrdersStatus.CONFIRMED.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    public void testCancelOrder_Success() throws Exception {
        OrderedItemDTO item = new OrderedItemDTO();
        item.setProductId(productId);
        item.setQuantity(1);

        List<OrderedItemDTO> items = new ArrayList<>();
        items.add(item);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setClientId(clientId);
        orderDTO.setRestaurantId(restaurantId);
        orderDTO.setItems(items);
        orderDTO.setDeliveryAddress("Rua Cancelar, 600");

        String response = mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/api/pedidos/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCalculateOrderTotal_Success() throws Exception {
        OrderedItemDTO item = new OrderedItemDTO();
        item.setProductId(productId);
        item.setQuantity(2);

        List<OrderedItemDTO> items = new ArrayList<>();
        items.add(item);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setClientId(clientId);
        orderDTO.setRestaurantId(restaurantId);
        orderDTO.setItems(items);
        orderDTO.setDeliveryAddress("Rua Calcular, 700");

        mockMvc.perform(post("/api/pedidos/calcular")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    public void testListOrders_Success() throws Exception {
        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetOrdersByRestaurant_Success() throws Exception {
        mockMvc.perform(get("/api/pedidos/restaurantes/" + restaurantId + "/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
