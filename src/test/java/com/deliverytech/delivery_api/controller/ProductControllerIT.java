package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.request.ProductDTO;
import com.deliverytech.delivery_api.dto.request.RestaurantDTO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long restaurantId;

    @BeforeEach
    public void setUp() throws Exception {
        
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName("Restaurante Teste Produtos");
        restaurantDTO.setCategory("Pizza");
        restaurantDTO.setAddress("Rua Teste, 123");
        restaurantDTO.setPhoneNumber("11987654321");
        restaurantDTO.setDeliveryFee(new BigDecimal("10.00"));

        String response = mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        restaurantId = objectMapper.readTree(response).get("id").asLong();
    }

    @Test
    public void testCreateProduct_Success() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Pizza Calabresa");
        productDTO.setDescription("Pizza tradicional com calabresa");
        productDTO.setPrice(new BigDecimal("35.00"));
        productDTO.setCategory("Pizza");
        productDTO.setRestaurantId(restaurantId);

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Pizza Calabresa"))
                .andExpect(jsonPath("$.price").value(35.00))
                .andExpect(jsonPath("$.category").value("Pizza"));
    }

    @Test
    public void testCreateProduct_ValidationError() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(""); 
        productDTO.setRestaurantId(restaurantId);

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetProductById_Success() throws Exception {
        
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Pizza Margherita");
        productDTO.setDescription("Pizza com mussarela e tomate");
        productDTO.setPrice(new BigDecimal("32.00"));
        productDTO.setCategory("Pizza");
        productDTO.setRestaurantId(restaurantId);

        String response = mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        
        mockMvc.perform(get("/api/produtos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza Margherita"))
                .andExpect(jsonPath("$.price").value(32.00));
    }

    @Test
    public void testGetProductById_NotFound() throws Exception {
        mockMvc.perform(get("/api/produtos/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateProduct_Success() throws Exception {
        
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Pizza Original");
        productDTO.setDescription("Descrição original");
        productDTO.setPrice(new BigDecimal("30.00"));
        productDTO.setCategory("Pizza");
        productDTO.setRestaurantId(restaurantId);

        String response = mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        
        ProductDTO updatedDTO = new ProductDTO();
        updatedDTO.setName("Pizza Atualizada");
        updatedDTO.setDescription("Descrição atualizada");
        updatedDTO.setPrice(new BigDecimal("40.00"));
        updatedDTO.setCategory("Pizza");
        updatedDTO.setRestaurantId(restaurantId);

        mockMvc.perform(put("/api/produtos/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza Atualizada"))
                .andExpect(jsonPath("$.price").value(40.00));
    }

    @Test
    public void testToggleProductAvailability_Success() throws Exception {
        
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Pizza Disponibilidade");
        productDTO.setDescription("Teste de disponibilidade");
        productDTO.setPrice(new BigDecimal("28.00"));
        productDTO.setCategory("Pizza");
        productDTO.setRestaurantId(restaurantId);

        String response = mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        
        mockMvc.perform(patch("/api/produtos/" + id + "/disponibilidade"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").isBoolean());
    }

    @Test
    public void testGetProductsByCategory_Success() throws Exception {
        mockMvc.perform(get("/api/produtos/categoria/Pizza"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testDeleteProduct_Success() throws Exception {
        
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Pizza Para Deletar");
        productDTO.setDescription("Será deletada");
        productDTO.setPrice(new BigDecimal("25.00"));
        productDTO.setCategory("Pizza");
        productDTO.setRestaurantId(restaurantId);

        String response = mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        
        mockMvc.perform(delete("/api/produtos/" + id))
                .andExpect(status().isNoContent());

        
        mockMvc.perform(get("/api/produtos/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSearchProductsByName_Success() throws Exception {
        
        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setName("Pizza Especial");
        productDTO1.setDescription("Pizza especial");
        productDTO1.setPrice(new BigDecimal("45.00"));
        productDTO1.setCategory("Pizza");
        productDTO1.setRestaurantId(restaurantId);

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO1)))
                .andExpect(status().isCreated());

        
        mockMvc.perform(get("/api/produtos/buscar")
                .param("nome", "Pizza"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
