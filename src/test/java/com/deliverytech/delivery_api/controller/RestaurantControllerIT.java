package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.request.RestaurantDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RestaurantControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateRestaurant_Success() throws Exception {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName("Restaurante Teste");
        restaurantDTO.setCategory("Pizza");
        restaurantDTO.setAddress("Rua Teste, 123");
        restaurantDTO.setPhoneNumber("11987654321");
        restaurantDTO.setDeliveryFee(new BigDecimal("10.00"));

        mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Restaurante Teste"))
                .andExpect(jsonPath("$.address").value("Rua Teste, 123"))
                .andExpect(jsonPath("$.category").value("Pizza"));
    }

    @Test
    public void testCreateRestaurant_ValidationError() throws Exception {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName("");

        mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetRestaurantById_Success() throws Exception {
    
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName("Restaurante Busca");
        restaurantDTO.setCategory("Hamburguer");
        restaurantDTO.setAddress("Rua Busca, 456");
        restaurantDTO.setPhoneNumber("11987654322");
        restaurantDTO.setDeliveryFee(new BigDecimal("8.00"));

        String response = mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

    
        mockMvc.perform(get("/api/restaurantes/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Restaurante Busca"))
                .andExpect(jsonPath("$.category").value("Hamburguer"));
    }

    @Test
    public void testGetRestaurantById_NotFound() throws Exception {
        mockMvc.perform(get("/api/restaurantes/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllRestaurants_Success() throws Exception {
        mockMvc.perform(get("/api/restaurantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetRestaurantsByCategory_Success() throws Exception {
        mockMvc.perform(get("/api/restaurantes/categoria/Pizza"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testUpdateRestaurant_Success() throws Exception {
    
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName("Restaurante Original");
        restaurantDTO.setCategory("Japonesa");
        restaurantDTO.setAddress("Rua Original, 789");
        restaurantDTO.setPhoneNumber("11987654323");
        restaurantDTO.setDeliveryFee(new BigDecimal("12.00"));

        String response = mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

    
        RestaurantDTO updatedDTO = new RestaurantDTO();
        updatedDTO.setName("Restaurante Atualizado");
        updatedDTO.setCategory("Japonesa");
        updatedDTO.setAddress("Rua Atualizada, 789");
        updatedDTO.setPhoneNumber("11987654323");
        updatedDTO.setDeliveryFee(new BigDecimal("12.00"));

        mockMvc.perform(put("/api/restaurantes/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Restaurante Atualizado"));
    }

    @Test
    public void testCalculateDeliveryFee_Success() throws Exception {
    
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName("Restaurante Taxa");
        restaurantDTO.setCategory("Italiana");
        restaurantDTO.setAddress("Rua Taxa, 111");
        restaurantDTO.setPhoneNumber("11987654324");
        restaurantDTO.setDeliveryFee(new BigDecimal("5.00"));

        String response = mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

    
        mockMvc.perform(get("/api/restaurantes/" + id + "/taxa-entrega/01310100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    public void testToggleRestaurantStatus_Success() throws Exception {
    
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName("Restaurante Status");
        restaurantDTO.setCategory("Italiana");
        restaurantDTO.setAddress("Rua Status, 222");
        restaurantDTO.setPhoneNumber("11987654325");
        restaurantDTO.setDeliveryFee(new BigDecimal("7.00"));

        String response = mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

    
        mockMvc.perform(patch("/api/restaurantes/" + id + "/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").isBoolean());
    }

    @Test
    public void testGetNearbyRestaurants_Success() throws Exception {
        mockMvc.perform(get("/api/restaurantes/proximos/01310100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetProductsByRestaurant_Success() throws Exception {
    
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName("Restaurante Produtos");
        restaurantDTO.setCategory("Vegetariana");
        restaurantDTO.setAddress("Rua Produtos, 333");
        restaurantDTO.setPhoneNumber("11987654326");
        restaurantDTO.setDeliveryFee(new BigDecimal("9.00"));

        String response = mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

    
        mockMvc.perform(get("/api/restaurantes/" + id + "/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
