package com.deliverytech.delivery_api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.dto.request.RestaurantDTO;
import com.deliverytech.delivery_api.dto.response.ProductResponseDTO;
import com.deliverytech.delivery_api.dto.response.RestaurantResponseDTO;
import com.deliverytech.delivery_api.service.api.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/restaurantes")
@Tag(name = "Restaurantes", description = "Endpoints de restaurantes")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastrar restaurante")
    @ApiResponse(responseCode = "201", description = "Restaurante criado")
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@Valid @RequestBody RestaurantDTO restaurant) {
        return ResponseEntity.status(201).body(restaurantService.createRestaurant(restaurant));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar restaurante por ID")
    @ApiResponse(responseCode = "200", description = "Restaurante encontrado")
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @GetMapping
    @Operation(summary = "Listar restaurantes ativos")
    @ApiResponse(responseCode = "200", description = "Lista de restaurantes ativos")
    public ResponseEntity<List<RestaurantResponseDTO>> getActiveRestaurants() {
        return ResponseEntity.ok(restaurantService.getActiveRestaurants());
    }


    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Buscar restaurantes por categoria")
    @ApiResponse(responseCode = "200", description = "Lista de restaurantes")
    public ResponseEntity<List<RestaurantResponseDTO>> getRestaurantByCategory(@PathVariable String categoria) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByCategory(categoria));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('RESTAURANT') and @restaurantServiceImpl.isOwner(#id))")
    @Operation(summary = "Atualizar restaurante")
    @ApiResponse(responseCode = "200", description = "Restaurante atualizado")
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantDTO restaurant) {    
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurant));
    }

    @GetMapping("/{id}/taxa-entrega/{cep}")
    @Operation(summary = "Calcular taxa de entrega")
    @ApiResponse(responseCode = "200", description = "Taxa calculada")
    public ResponseEntity<BigDecimal> calculateDeliveryFee(@PathVariable Long id, @PathVariable String cep) {
        return ResponseEntity.ok(restaurantService.calculateDeliveryFee(id, cep));
    }

    @GetMapping("/{restauranteId}/produtos")
    @Operation(summary = "Listar produtos do restaurante")
    @ApiResponse(responseCode = "200", description = "Lista de produtos")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByRestaurant(@PathVariable Long restauranteId) {
        return ResponseEntity.ok(restaurantService.getProductsByRestaurant(restauranteId));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Ativar/desativar restaurante",
        description = "Alterna o status de ativo/inativo do restaurante"
    )
    @ApiResponse(responseCode = "204", description = "Status atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    public ResponseEntity<Void> toggleRestaurantStatus(
        @Parameter(description = "ID do restaurante", example = "1", required = true)
        @PathVariable Long id
    ) {
        restaurantService.toggleRestaurantStatus(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/proximos/{cep}")
    @Operation(
        summary = "Buscar restaurantes próximos",
        description = "Retorna restaurantes ativos próximos ao CEP informado, ordenados por avaliação"
    )
    @ApiResponse(responseCode = "200", description = "Lista de restaurantes próximos")
    @ApiResponse(responseCode = "400", description = "CEP inválido")
    public ResponseEntity<List<RestaurantResponseDTO>> getNearbyRestaurants(
        @Parameter(description = "CEP para busca de proximidade", example = "01310100", required = true)
        @PathVariable String cep
    ) {
        return ResponseEntity.ok(restaurantService.getNearbyRestaurants(cep));
    }
}
