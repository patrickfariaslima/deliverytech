package com.deliverytech.delivery_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.dto.request.ClientDTO;
import com.deliverytech.delivery_api.dto.response.ClientResponseDTO;
import com.deliverytech.delivery_api.dto.response.OrderSummaryResponseDTO;
import com.deliverytech.delivery_api.service.api.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Clientes", description = "Endpoints de clientes")
@RestController
@RequestMapping("/api/clientes")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Cadastrar Cliente")
    @ApiResponse(responseCode = "201", description = "Cliente criado")
    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@Valid @RequestBody ClientDTO client) {
        return ResponseEntity.status(201).body(clientService.createClient((client)));
    }

    @Operation(summary = "Buscar cliente por ID")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @Operation(summary = "Listar clientes ativos")
    @ApiResponse(responseCode = "200", description = "Lista de clientes ativos")
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getActiveClients() {
        return ResponseEntity.ok(clientService.getActiveClients());
    }
    
    @Operation(summary = "Atualizar cliente")
    @ApiResponse(responseCode = "200", description = "Cliente atualizado")
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id, @Valid @RequestBody ClientDTO client) {
        return ResponseEntity.ok(clientService.updateClient(id, client));
    }

    @Operation(summary = "Ativar/desativar cliente")
    @ApiResponse(responseCode = "204", description = "Status atualizado")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> toggleClientStatus(@PathVariable Long id) {
        clientService.toggleClientStatus(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar cliente por email")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")
    @GetMapping("/email/{email}")
    public ResponseEntity<ClientResponseDTO> getClientByEmail(@PathVariable String email) {
        return ResponseEntity.ok(clientService.getClientByEmail(email));
    }

    @Operation(summary = "Listar pedidos do cliente")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos")
    @GetMapping("/{clienteId}/pedidos")
    public ResponseEntity<List<OrderSummaryResponseDTO>> getOrdersByClient(@PathVariable Long clienteId) {
        return ResponseEntity.ok(clientService.getOrdersByClient(clienteId));
    }
    
    @Operation(summary = "Buscar clientes por nome")
    @ApiResponse(responseCode = "200", description = "Lista de clientes")
    @GetMapping("/buscar")
    public ResponseEntity<List<ClientResponseDTO>> getClientByName(@RequestParam String name) {
        return ResponseEntity.ok(clientService.getClientByName(name));
    }
}
