package com.deliverytech.delivery_api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.dto.request.ClientDTO;
import com.deliverytech.delivery_api.dto.response.ClientResponseDTO;
import com.deliverytech.delivery_api.exceptions.EntityNotFoundException;
import com.deliverytech.delivery_api.model.Client;
import com.deliverytech.delivery_api.repository.ClientRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional // Se ocorre erro, ocorre rollback, se tudo ok, commit
public class ClientService {
    private final ClientRepository repository;
    private final ModelMapper mapper;

    public ClientService(ClientRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ClientResponseDTO createClient(ClientDTO dto) {
        if(repository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        Client client =mapper.map(dto, Client.class);

        client.setActive(true);
        client.setRegisteredAt(LocalDateTime.now());

        Client saved = repository.save(client);
        
        return mapper.map(saved, ClientResponseDTO.class);
    }

    public List<ClientResponseDTO> getActiveClients() {    
        return repository.findByActiveTrue()
        .stream()
        .map(client -> mapper.map(client, ClientResponseDTO.class))
        .toList();
    }

    public List<ClientResponseDTO> getClientByName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
        .stream()
        .map(client -> mapper.map(client, ClientResponseDTO.class))
        .toList();
    }

    public ClientResponseDTO getClientById(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        return mapper.map(client, ClientResponseDTO.class);
    }

    public ClientResponseDTO updateClient(long id, ClientDTO updatedClient) {
        Client existingClient = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found"));

        existingClient.setName(updatedClient.getName());

        existingClient.setPhoneNumber(updatedClient.getPhoneNumber());

        existingClient.setAddress(updatedClient.getAddress());

        existingClient.setEmail(updatedClient.getEmail());

        Client saved = repository.save(existingClient);

        return mapper.map(saved, ClientResponseDTO.class);
    }

    public void inactivateClient(long id) {
        ClientResponseDTO existingClient = getClientById(id);
        existingClient.setActive(false);

        Client client = mapper.map(existingClient, Client.class);
        repository.save(client);
    }

    public boolean validateEmail(String email) {
        return repository.existsByEmail(email);
    }
}
