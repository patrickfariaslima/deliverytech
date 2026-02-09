package com.deliverytech.delivery_api.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.dto.request.ClientDTO;
import com.deliverytech.delivery_api.dto.response.ClientResponseDTO;
import com.deliverytech.delivery_api.dto.response.OrderSummaryResponseDTO;
import com.deliverytech.delivery_api.exceptions.BusinessException;
import com.deliverytech.delivery_api.exceptions.EntityNotFoundException;
import com.deliverytech.delivery_api.model.Client;
import com.deliverytech.delivery_api.repository.ClientRepository;
import com.deliverytech.delivery_api.service.api.ClientService;
import com.deliverytech.delivery_api.service.api.OrderService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    private final ClientRepository repository;
    private final ModelMapper mapper;
    private final OrderService orderService;
    private static final String NOT_FOUND_MESSAGE = "Client not found";

    public ClientServiceImpl(ClientRepository repository, ModelMapper mapper, OrderService orderService) {
        this.repository = repository;
        this.mapper = mapper;
        this.orderService = orderService;
    }

    @Override
    public ClientResponseDTO createClient(ClientDTO dto) {
        if(repository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email already in use");
        }

        Client client = mapper.map(dto, Client.class);

        client.setActive(true);
        client.setRegisteredAt(LocalDateTime.now());

        return mapper.map(repository.save(client), ClientResponseDTO.class);
    }

    @Override
    public ClientResponseDTO getClientById(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        return mapper.map(client, ClientResponseDTO.class);
    }

    @Override
    public ClientResponseDTO getClientByEmail(String email) {
        Client client = repository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        return mapper.map(client, ClientResponseDTO.class);
    }

    @Override
    public ClientResponseDTO updateClient(Long id, ClientDTO updatedClient) {
        Client client = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));

         if (!client.getEmail().equals(updatedClient.getEmail()) && repository.existsByEmail(updatedClient.getEmail())) {
            throw new BusinessException("Email already in use");
        }

        client.setName(updatedClient.getName());
        client.setEmail(updatedClient.getEmail());
        client.setPhoneNumber(updatedClient.getPhoneNumber());
        client.setAddress(updatedClient.getAddress());

        return mapper.map(repository.save(client), ClientResponseDTO.class);
    }

    @Override
    public ClientResponseDTO toggleClientStatus(Long id) {
        Client client = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));

        client.setActive(!client.getActive());

        return mapper.map(repository.save(client), ClientResponseDTO.class);
    }

    @Override
    public List<ClientResponseDTO> getActiveClients() {    
        return repository.findByActiveTrue()
        .stream()
        .map(client -> mapper.map(client, ClientResponseDTO.class))
        .toList();
    }

    @Override
    public List<ClientResponseDTO> getClientByName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
        .stream()
        .map(client -> mapper.map(client, ClientResponseDTO.class))
        .toList();
    }

    @Override
    public List<OrderSummaryResponseDTO> getOrdersByClient(Long clientId) {
        repository.findById(clientId)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        return orderService.getOrdersByClient(clientId);
    }
}
