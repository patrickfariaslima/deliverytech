package com.deliverytech.delivery_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.dto.request.ClientDTO;
import com.deliverytech.delivery_api.dto.response.ClientResponseDTO;
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
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody ClientDTO client) {
        return ResponseEntity.status(201).body(clientService.createClient((client)));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ClientResponseDTO>> getActiveClients() {
        return ResponseEntity.ok(clientService.getActiveClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable String id) {
        return ResponseEntity.ok(clientService.getClientById(Long.parseLong(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable String id, @RequestBody ClientDTO client) {
        return ResponseEntity.ok(clientService.updateClient(Long.parseLong(id), client));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inactivateClient(@PathVariable String id) {
        clientService.inactivateClient(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ClientResponseDTO>> getClientByName(@RequestParam String name) {
        return ResponseEntity.ok(clientService.getClientByName(name));
    }
}
