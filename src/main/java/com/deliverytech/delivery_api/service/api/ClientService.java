package com.deliverytech.delivery_api.service.api;

import java.util.List;

import com.deliverytech.delivery_api.dto.request.ClientDTO;
import com.deliverytech.delivery_api.dto.response.ClientResponseDTO;
import com.deliverytech.delivery_api.dto.response.OrderSummaryResponseDTO;

public interface ClientService {
    ClientResponseDTO createClient(ClientDTO dto);
    ClientResponseDTO getClientById(Long clientId);
    ClientResponseDTO getClientByEmail(String email);
    ClientResponseDTO updateClient(Long clientId, ClientDTO dto);
    ClientResponseDTO toggleClientStatus(Long clientId);
    List<ClientResponseDTO> getActiveClients();
    List<ClientResponseDTO> getClientByName(String name);
    List<OrderSummaryResponseDTO> getOrdersByClient(Long clientId);
}
