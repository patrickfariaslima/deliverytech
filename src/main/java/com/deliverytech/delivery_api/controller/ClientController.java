package com.deliverytech.delivery_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.model.Client;
import com.deliverytech.delivery_api.service.ClientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/clientes")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        return ResponseEntity.status(201).body(clientService.createClient((client)));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Client>> getActiveClients() {
        return ResponseEntity.ok(clientService.getActiveClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable String id) {
        Client client = clientService.getClientById(Long.parseLong(id));
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable String id, @RequestBody Client client) {
        Client updatedClient = clientService.updateClient(Long.parseLong(id), client);
        return updatedClient;
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inactivateClient(@PathVariable String id) {
        clientService.inactivateClient(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Client>> getClientByName(@RequestParam String name) {
        return ResponseEntity.ok(clientService.getClientByName(name));
    }
}
