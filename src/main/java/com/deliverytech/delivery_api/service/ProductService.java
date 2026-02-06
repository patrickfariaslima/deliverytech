package com.deliverytech.delivery_api.service;

import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.dto.request.ProductDTO;
import com.deliverytech.delivery_api.dto.response.ProductResponseDTO;
import com.deliverytech.delivery_api.exceptions.BusinessException;
import com.deliverytech.delivery_api.exceptions.EntityNotFoundException;
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
    private final ModelMapper mapper;

    public ProductService(ProductRepository productRepository, RestaurantRepository restaurantRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.restaurantRepository = restaurantRepository;
        this.mapper = mapper;
    }

    public ProductResponseDTO createProduct(Long restaurantId, ProductDTO product) {
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Price must be greater than zero");
        }

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        Product newProduct = mapper.map(product, Product.class);
        newProduct.setAvailable(true);
        newProduct.setRestaurant(restaurant);

        Product saved = productRepository.save(newProduct);
        return toResponse(saved);
    }

    public List<ProductResponseDTO> getProductsByRestaurant(Long restaurantId) {
        List<Product> products = productRepository.findByRestaurantId(restaurantId);
        return products.stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return toResponse(product);
    }

    private ProductResponseDTO toResponse(Product product) {
        ProductResponseDTO dto = mapper.map(product, ProductResponseDTO.class);
        if (product.getRestaurant() != null) {
            dto.setRestaurantId(product.getRestaurant().getId());
        }
        return dto;
    }
}
