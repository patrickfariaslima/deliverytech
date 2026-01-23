package com.deliverytech.delivery_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.model.Restaurant;
import com.deliverytech.delivery_api.repository.RestaurantRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RestaurantService {
    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        validateRestaurant(restaurant);
        restaurant.setActive(true);
        return repository.save(restaurant);
    }

    public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {
        Restaurant existingRestaurant = getRestaurantById(id);
        
        validateRestaurant(updatedRestaurant);
        
        existingRestaurant.setName(updatedRestaurant.getName());
        existingRestaurant.setCategory(updatedRestaurant.getCategory());
        existingRestaurant.setAddress(updatedRestaurant.getAddress());
        existingRestaurant.setPhoneNumber(updatedRestaurant.getPhoneNumber());
        existingRestaurant.setRating(updatedRestaurant.getRating());
        existingRestaurant.setDeliveryFee(updatedRestaurant.getDeliveryFee());
        
        return repository.save(existingRestaurant);
    }

    public List<Restaurant> getActiveRestaurants() {
        return repository.findByActiveTrue();
    }

    public List<Restaurant> getRestaurantByCategory(String category) {
        return repository.findByCategoryAndActiveTrue(category);
    }

    public Restaurant getRestaurantById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with id: " + id));
    }

    public void inactivateRestaurant(Long id) {
        Restaurant restaurant = getRestaurantById(id);
        restaurant.setActive(false);
        repository.save(restaurant);
    }

    public void activateRestaurant(Long id) {
        Restaurant restaurant = getRestaurantById(id);
        restaurant.setActive(true);
        repository.save(restaurant);
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (restaurant.getName() == null || restaurant.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Restaurant name is required");
        }
        
        if (restaurant.getCategory() == null || restaurant.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Restaurant category is required");
        }
        
        if (restaurant.getAddress() == null || restaurant.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Restaurant address is required");
        }
        
        if (restaurant.getPhoneNumber() == null || restaurant.getPhoneNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Restaurant phone number is required");
        }
        
        if (restaurant.getDeliveryFee() != null && restaurant.getDeliveryFee().signum() < 0) {
            throw new IllegalArgumentException("Delivery fee cannot be negative");
        }
        
        if (restaurant.getRating() != null && 
            (restaurant.getRating().signum() < 0 || restaurant.getRating().compareTo(new java.math.BigDecimal("5")) > 0)) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
    }
}
