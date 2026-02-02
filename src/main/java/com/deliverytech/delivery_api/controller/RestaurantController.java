package com.deliverytech.delivery_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deliverytech.delivery_api.dto.request.RestaurantDTO;
import com.deliverytech.delivery_api.dto.response.RestaurantResponseDTO;
import com.deliverytech.delivery_api.service.RestaurantService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/restaurantes")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@RequestBody RestaurantDTO restaurant) {
        return ResponseEntity.status(201).body(restaurantService.createRestaurant(restaurant));
    }

    @GetMapping("/listar")
    public List<RestaurantResponseDTO> getActiveRestaurants() {
        return restaurantService.getActiveRestaurants();
    }

    @GetMapping("/{id}")
    public RestaurantResponseDTO getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @DeleteMapping("/{id}/inativar")
    public void inactivateRestaurant(@PathVariable Long id) {
        restaurantService.inactivateRestaurant(id);
    }
}
