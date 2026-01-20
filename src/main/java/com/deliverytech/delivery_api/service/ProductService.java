package com.deliverytech.delivery_api.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.model.Product;
import com.deliverytech.delivery_api.model.Restaurant;
import com.deliverytech.delivery_api.repository.ProductRepository;
import com.deliverytech.delivery_api.repository.RestaurantRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;

    public ProductService(ProductRepository productRepository, RestaurantRepository restaurantRepository) {
        this.productRepository = productRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Product createProduct(Long restaurantId, Product product) {
        if(product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        product.setAvailable(true);
        product.setRestaurant(restaurant);

        return productRepository.save(product);
    }

    public List<Product> getProductsByRestaurant(Long restaurantId) {
        return productRepository.findByRestaurantId(restaurantId);
    }
}
