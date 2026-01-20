package com.deliverytech.delivery_api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.model.Client;
import com.deliverytech.delivery_api.repository.ClientRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional // Se ocorre erro, ocorre rollback, se tudo ok, commit
public class ClientService {
    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Client createClient(Client client) {
        if(repository.existsByEmail(client.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        client.setActive(true);
        client.setRegisteredAt(LocalDateTime.now());
        
        return repository.save(client);
    }

    public List<Client> getActiveClients() {
        return repository.findByActiveTrue();
    }

    public List<Client> getClientByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public Client getClientById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
    }

    public Client updateClient(long id, Client updatedClient) {
        Client existingClient = getClientById(id);

        existingClient.setName(updatedClient.getName());

        existingClient.setPhoneNumber(updatedClient.getPhoneNumber());

        existingClient.setAddress(updatedClient.getAddress());

        existingClient.setEmail(updatedClient.getEmail());

        return repository.save(existingClient);
    }

    public void inactivateClient(long id) {
        Client existingClient = getClientById(id);
        existingClient.setActive(false);
        repository.save(existingClient);
    }


    public boolean validateEmail(String email) {
        return repository.existsByEmail(email);
    }
}
