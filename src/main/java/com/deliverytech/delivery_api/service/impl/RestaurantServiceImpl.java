package com.deliverytech.delivery_api.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.dto.request.RestaurantDTO;
import com.deliverytech.delivery_api.dto.response.ProductResponseDTO;
import com.deliverytech.delivery_api.dto.response.RestaurantResponseDTO;
import com.deliverytech.delivery_api.exceptions.BusinessException;
import com.deliverytech.delivery_api.model.Restaurant;
import com.deliverytech.delivery_api.repository.RestaurantRepository;
import com.deliverytech.delivery_api.service.api.ProductService;
import com.deliverytech.delivery_api.service.api.RestaurantService;

import com.deliverytech.delivery_api.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;
    private final ModelMapper mapper;
    private final ProductService productService;
    private static final String NOT_FOUND_MESSAGE = "Restaurant not found";

    public RestaurantServiceImpl(RestaurantRepository repository, ModelMapper mapper, ProductService productService) {
        this.repository = repository;
        this.mapper = mapper;
        this.productService = productService;
    }

    @Override
    public RestaurantResponseDTO createRestaurant(RestaurantDTO dto) {
        Restaurant restaurant = mapper.map(dto, Restaurant.class);
        restaurant.setActive(true);

        return mapper.map(repository.save(restaurant), RestaurantResponseDTO.class);
    }

    @Override
    public RestaurantResponseDTO getRestaurantById(Long id) {
        Restaurant restaurant = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        return mapper.map(restaurant, RestaurantResponseDTO.class);
    }

    @Override
    public List<RestaurantResponseDTO> getRestaurantsByCategory(String category) {
        return repository.findByCategory(category).stream()
            .map(r -> mapper.map(r, RestaurantResponseDTO.class))
            .toList();
    }

    @Override
    public List<RestaurantResponseDTO> getActiveRestaurants() {
        return repository.findByActiveTrue().stream()
            .map(r -> mapper.map(r, RestaurantResponseDTO.class))
            .toList();
    }

    @Override
    public RestaurantResponseDTO updateRestaurant(Long id, RestaurantDTO updatedRestaurant) {
        Restaurant restaurant = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        
        restaurant.setName(updatedRestaurant.getName());
        restaurant.setCategory(updatedRestaurant.getCategory());
        restaurant.setAddress(updatedRestaurant.getAddress());
        restaurant.setPhoneNumber(updatedRestaurant.getPhoneNumber());
        restaurant.setDeliveryFee(updatedRestaurant.getDeliveryFee());

        return mapper.map(repository.save(restaurant), RestaurantResponseDTO.class);
    }

    @Override
    public BigDecimal calculateDeliveryFee(Long restaurantId, String cep) {
        if (cep == null || cep.isBlank()) {
            throw new BusinessException("Invalid CEP");
        }
        Restaurant restaurant = repository.findById(restaurantId)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));

        BigDecimal base = restaurant.getDeliveryFee() == null ? BigDecimal.ZERO : restaurant.getDeliveryFee();
        
        return cep.startsWith("1") ? base.add(new BigDecimal("2.00")) : base.add(new BigDecimal("5.00"));
    }

    @Override
    public List<ProductResponseDTO> getProductsByRestaurant(Long restaurantId) {
        repository.findById(restaurantId)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        return productService.getProductsByRestaurant(restaurantId);
    }

    @Override
    public void toggleRestaurantStatus(Long restaurantId) {
        Restaurant restaurant = repository.findById(restaurantId)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        restaurant.setActive(!restaurant.getActive());
        repository.save(restaurant);
    }

    @Override
    public List<RestaurantResponseDTO> getNearbyRestaurants(String cep) {
        List<Restaurant> restaurants = repository.findByActiveTrueOrderByRatingDesc();
        return restaurants.stream()
            .map(r -> mapper.map(r, RestaurantResponseDTO.class))
            .toList();
    }

    @Override
    public boolean isOwner(Long restaurantId) {
        com.deliverytech.delivery_api.model.User currentUser = com.deliverytech.delivery_api.security.SecurityUtils.getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        return currentUser.getRestaurantId() != null && 
               currentUser.getRestaurantId().equals(restaurantId);
    }
}
