package com.deliverytech.delivery_api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.dto.request.RestaurantDTO;
import com.deliverytech.delivery_api.dto.response.ProductResponseDTO;
import com.deliverytech.delivery_api.dto.response.RestaurantResponseDTO;
import com.deliverytech.delivery_api.service.api.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
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
    
}
