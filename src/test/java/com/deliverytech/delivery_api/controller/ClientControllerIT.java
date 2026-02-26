package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.request.ClientDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClientControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateClient_Success() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("João Silva");
        clientDTO.setEmail("joao.silva@example.com");
        clientDTO.setPhoneNumber("11987654321");
        clientDTO.setAddress("Rua das Flores, 123");

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao.silva@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("11987654321"));
    }

    @Test
    public void testCreateClient_ValidationError_InvalidEmail() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("João Silva");
        clientDTO.setEmail("email-invalido"); 
        clientDTO.setPhoneNumber("11987654321");
        clientDTO.setAddress("Rua das Flores, 123");

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateClient_ValidationError_EmptyName() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(""); 
        clientDTO.setEmail("joao@example.com");
        clientDTO.setPhoneNumber("11987654321");

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetClientById_Success() throws Exception {
        
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("Maria Santos");
        clientDTO.setEmail("maria.santos@example.com");
        clientDTO.setPhoneNumber("11987654322");
        clientDTO.setAddress("Avenida Paulista, 456");

        String response = mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        
        mockMvc.perform(get("/api/clientes/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maria Santos"))
                .andExpect(jsonPath("$.email").value("maria.santos@example.com"));
    }

    @Test
    public void testGetClientById_NotFound() throws Exception {
        mockMvc.perform(get("/api/clientes/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllClients_Success() throws Exception {
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testUpdateClient_Success() throws Exception {
        
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("Pedro Oliveira");
        clientDTO.setEmail("pedro.oliveira@example.com");
        clientDTO.setPhoneNumber("11987654323");
        clientDTO.setAddress("Rua Original, 789");

        String response = mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        
        ClientDTO updatedDTO = new ClientDTO();
        updatedDTO.setName("Pedro Oliveira Silva");
        updatedDTO.setEmail("pedro.oliveira@example.com");
        updatedDTO.setPhoneNumber("11987654323");
        updatedDTO.setAddress("Rua Atualizada, 789");

        mockMvc.perform(put("/api/clientes/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pedro Oliveira Silva"))
                .andExpect(jsonPath("$.address").value("Rua Atualizada, 789"));
    }

    @Test
    public void testDeleteClient_Success() throws Exception {
        
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("Ana Costa");
        clientDTO.setEmail("ana.costa@example.com");
        clientDTO.setPhoneNumber("11987654324");
        clientDTO.setAddress("Rua Delete, 111");

        String response = mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        
        mockMvc.perform(delete("/api/clientes/" + id))
                .andExpect(status().isNoContent());

        
        mockMvc.perform(get("/api/clientes/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetClientByEmail_Success() throws Exception {
        
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("Carlos Mendes");
        clientDTO.setEmail("carlos.mendes@example.com");
        clientDTO.setPhoneNumber("11987654325");
        clientDTO.setAddress("Rua Email, 222");

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated());

        
        mockMvc.perform(get("/api/clientes/email/carlos.mendes@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Carlos Mendes"));
    }

    @Test
    public void testGetClientOrderHistory_Success() throws Exception {
        
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("Lucia Torres");
        clientDTO.setEmail("lucia.torres@example.com");
        clientDTO.setPhoneNumber("11987654326");
        clientDTO.setAddress("Rua Histórico, 333");

        String response = mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        
        mockMvc.perform(get("/api/clientes/" + id + "/historico-pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetClientTotalSpent_Success() throws Exception {
        
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("Fernando Lima");
        clientDTO.setEmail("fernando.lima@example.com");
        clientDTO.setPhoneNumber("11987654327");
        clientDTO.setAddress("Rua Gasto, 444");

        String response = mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        
        mockMvc.perform(get("/api/clientes/" + id + "/total-gasto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    public void testSearchClientsByName_Success() throws Exception {
        
        ClientDTO clientDTO1 = new ClientDTO();
        clientDTO1.setName("Roberto Alves");
        clientDTO1.setEmail("roberto.alves@example.com");
        clientDTO1.setPhoneNumber("11987654328");
        clientDTO1.setAddress("Rua Busca, 555");

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO1)))
                .andExpect(status().isCreated());

        ClientDTO clientDTO2 = new ClientDTO();
        clientDTO2.setName("Roberto Silva");
        clientDTO2.setEmail("roberto.silva@example.com");
        clientDTO2.setPhoneNumber("11987654329");
        clientDTO2.setAddress("Rua Busca, 666");

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO2)))
                .andExpect(status().isCreated());

        
        mockMvc.perform(get("/api/clientes/buscar")
                .param("nome", "Roberto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetFavoriteRestaurant_Success() throws Exception {
        
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("Beatriz Santos");
        clientDTO.setEmail("beatriz.santos@example.com");
        clientDTO.setPhoneNumber("11987654330");
        clientDTO.setAddress("Rua Favorito, 777");

        String response = mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        
        mockMvc.perform(get("/api/clientes/" + id + "/restaurante-favorito"))
                .andExpect(status().isOk());
    }
}
