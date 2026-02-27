package com.deliverytech.delivery_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.deliverytech.delivery_api.dto.request.OrderDTO;
import com.deliverytech.delivery_api.dto.request.OrderedItemDTO;
import com.deliverytech.delivery_api.dto.response.OrderResponseDTO;
import com.deliverytech.delivery_api.enums.OrdersStatus;
import com.deliverytech.delivery_api.service.api.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Endpoints de pedidos")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    @Operation(summary = "Criar pedido")
    @ApiResponse(responseCode = "201", description = "Pedido criado")
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderDTO order) {
        return ResponseEntity.status(201).body(orderService.createOrder(order));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@orderServiceImpl.canAccess(#id)")
    @Operation(summary = "Buscar pedido por ID")
    @ApiResponse(responseCode = "200", description = "Pedido encontrado")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
    
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT')")
    @Operation(summary = "Atualizar status do pedido")
    @ApiResponse(responseCode = "200", description = "Status atualizado")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable Long id, @RequestParam OrdersStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    @Operation(summary = "Cancelar pedido")
    @ApiResponse(responseCode = "200", description = "Pedido cancelado")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    @PostMapping("/calcular")
    @Operation(summary = "Calcular total do pedido")
    @ApiResponse(responseCode = "200", description = "Total calculado")
    public ResponseEntity<BigDecimal> calculateTotal(@RequestBody List<OrderedItemDTO> items) {
        return ResponseEntity.ok(orderService.calculateOrderTotal(items));
    }

    @GetMapping("/meus")
    @PreAuthorize("hasRole('CLIENT')")
    @Operation(
        summary = "Meus pedidos",
        description = "Retorna todos os pedidos do cliente logado"
    )
    @ApiResponse(responseCode = "200", description = "Lista de pedidos do cliente")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders() {
        return ResponseEntity.ok(orderService.getMyOrders());
    }

    @GetMapping("/restaurante")
    @PreAuthorize("hasRole('RESTAURANT')")
    @Operation(
        summary = "Pedidos do meu restaurante",
        description = "Retorna todos os pedidos recebidos pelo restaurante do usuário logado"
    )
    @ApiResponse(responseCode = "200", description = "Lista de pedidos do restaurante")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    public ResponseEntity<List<OrderResponseDTO>> getMyRestaurantOrders() {
        return ResponseEntity.ok(orderService.getMyRestaurantOrders());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Listar pedidos",
        description = "Lista todos os pedidos com filtros opcionais de status e período"
    )
    @ApiResponse(responseCode = "200", description = "Lista de pedidos (pode estar vazia)")
    public ResponseEntity<List<OrderResponseDTO>> listOrders(
        @Parameter(description = "Filtrar por status do pedido") 
        @RequestParam(required = false) OrdersStatus status,
        @Parameter(description = "Data inicial no formato yyyy-MM-dd", example = "2024-01-01")
        @RequestParam(required = false) String startDate,
        @Parameter(description = "Data final no formato yyyy-MM-dd", example = "2024-12-31")
        @RequestParam(required = false) String endDate
    ) {
        return ResponseEntity.ok(orderService.listOrders(status, startDate, endDate));
    }

    @GetMapping("/restaurantes/{restauranteId}/pedidos")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('RESTAURANT') and @restaurantServiceImpl.isOwner(#restauranteId))")
    @Operation(
        summary = "Pedidos do restaurante",
        description = "Retorna todos os pedidos de um restaurante específico"
    )
    @ApiResponse(responseCode = "200", description = "Lista de pedidos do restaurante")
    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    public ResponseEntity<List<OrderResponseDTO>> getRestaurantOrders(
        @Parameter(description = "ID do restaurante", example = "1", required = true)
        @PathVariable Long restauranteId
    ) {
        return ResponseEntity.ok(orderService.getOrdersByRestaurant(restauranteId));
    }
}
