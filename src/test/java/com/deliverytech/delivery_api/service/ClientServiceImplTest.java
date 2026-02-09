package com.deliverytech.delivery_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.deliverytech.delivery_api.dto.request.ClientDTO;
import com.deliverytech.delivery_api.dto.response.ClientResponseDTO;
import com.deliverytech.delivery_api.dto.response.OrderSummaryResponseDTO;
import com.deliverytech.delivery_api.exceptions.BusinessException;
import com.deliverytech.delivery_api.exceptions.EntityNotFoundException;
import com.deliverytech.delivery_api.model.Client;
import com.deliverytech.delivery_api.repository.ClientRepository;
import com.deliverytech.delivery_api.service.api.OrderService;
import com.deliverytech.delivery_api.service.impl.ClientServiceImpl;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void createClient_shouldCreateWhenEmailIsUnique() {
        ClientDTO dto = new ClientDTO();
        dto.setEmail("a@b.com");

        Client entity = new Client();
        ClientResponseDTO response = new ClientResponseDTO();

        when(clientRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(mapper.map(dto, Client.class)).thenReturn(entity);
        when(clientRepository.save(entity)).thenReturn(entity);
        when(mapper.map(entity, ClientResponseDTO.class)).thenReturn(response);

        ClientResponseDTO result = clientService.createClient(dto);

        assertNotNull(result);
        verify(clientRepository).save(entity);
    }

    @Test
    void createClient_shouldThrowWhenEmailExists() {
        ClientDTO dto = new ClientDTO();
        dto.setEmail("a@b.com");

        when(clientRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(BusinessException.class, () -> clientService.createClient(dto));
    }

    @Test
    void getClientById_shouldThrowWhenNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> clientService.getClientById(1L));
    }

    @Test
    void getOrdersByClient_shouldDelegateToOrderService() {
        Client client = new Client();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(orderService.getOrdersByClient(1L)).thenReturn(List.of(new OrderSummaryResponseDTO()));

        List<OrderSummaryResponseDTO> result = clientService.getOrdersByClient(1L);

        assertEquals(1, result.size());
        verify(orderService).getOrdersByClient(1L);
    }
}