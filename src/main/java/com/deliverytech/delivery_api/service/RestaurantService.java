package com.deliverytech.delivery_api.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.dto.request.RestaurantDTO;
import com.deliverytech.delivery_api.dto.response.RestaurantResponseDTO;
import com.deliverytech.delivery_api.model.Restaurant;
import com.deliverytech.delivery_api.repository.RestaurantRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RestaurantService {
    private final RestaurantRepository repository;
    private final ModelMapper mapper;

    public RestaurantService(RestaurantRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public RestaurantResponseDTO createRestaurant(RestaurantDTO dto) {
        Restaurant restaurant = mapper.map(dto, Restaurant.class);
        restaurant.setActive(true);

        Restaurant saved = repository.save(restaurant);
        return mapper.map(saved, RestaurantResponseDTO.class);
    }

    public RestaurantResponseDTO updateRestaurant(Long id, RestaurantDTO updatedRestaurant) {
        Restaurant existingRestaurant = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
        
        existingRestaurant.setName(updatedRestaurant.getName());
        existingRestaurant.setCategory(updatedRestaurant.getCategory());
        existingRestaurant.setAddress(updatedRestaurant.getAddress());
        existingRestaurant.setPhoneNumber(updatedRestaurant.getPhoneNumber());
        existingRestaurant.setDeliveryFee(updatedRestaurant.getDeliveryFee());

        Restaurant saved = repository.save(existingRestaurant);
        
        return mapper.map(saved, RestaurantResponseDTO.class);
    }

    public List<RestaurantResponseDTO> getActiveRestaurants() {
        List<Restaurant> activeRestaurants = repository.findByActiveTrue();
        List<RestaurantResponseDTO> response = activeRestaurants.stream()
            .map(restaurant -> mapper.map(restaurant, RestaurantResponseDTO.class))
            .toList();
        return response;
    }

    public List<RestaurantResponseDTO> getRestaurantByCategory(String category) {
        List<Restaurant> restaurants = repository.findByCategoryAndActiveTrue(category);
        List<RestaurantResponseDTO> response = restaurants.stream()
            .map(restaurant -> mapper.map(restaurant, RestaurantResponseDTO.class))
            .toList();
        return response;
    }

    public RestaurantResponseDTO getRestaurantById(Long id) {
        Restaurant restaurant = repository.getById(id);
        return mapper.map(restaurant, RestaurantResponseDTO.class);
    }

    public void inactivateRestaurant(Long id) {
        Restaurant restaurant = repository.getById(id);
        restaurant.setActive(false);
        repository.save(restaurant);
    }

    public void activateRestaurant(Long id) {
        Restaurant restaurant = repository.getById(id);
        restaurant.setActive(true);
        repository.save(restaurant);
    }
}
