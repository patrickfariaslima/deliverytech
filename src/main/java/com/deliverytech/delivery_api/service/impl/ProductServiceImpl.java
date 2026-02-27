package com.deliverytech.delivery_api.service.impl;

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
import com.deliverytech.delivery_api.service.api.ProductService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper mapper;
    private static final String NOT_FOUND_MESSAGE = "Product not found";

    public ProductServiceImpl(ProductRepository productRepository, RestaurantRepository restaurantRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.restaurantRepository = restaurantRepository;
        this.mapper = mapper;
    }

    @Override
    public ProductResponseDTO createProduct(ProductDTO product) {
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Price must be greater than zero");
        }

        Restaurant restaurant = restaurantRepository.findById(product.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        Product newProduct = mapper.map(product, Product.class);
        newProduct.setAvailable(true);
        newProduct.setRestaurant(restaurant);

        return toResponse(productRepository.save(newProduct));
    }

    @Override
    public List<ProductResponseDTO> getProductsByRestaurant(Long restaurantId) {
        List<Product> products = productRepository.findByRestaurantIdAndAvailableTrue(restaurantId);
        return products.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));

        if (!Boolean.TRUE.equals(product.getAvailable())) {
            throw new BusinessException("Product is not available");
        }
        return toResponse(product);
    }

    @Override
    public ProductResponseDTO updateProduct(Long productId, ProductDTO dto) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());

        return toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO setProductAvailability(Long productId, boolean available) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));

        product.setAvailable(available);
        return toResponse(productRepository.save(product));
    }

    @Override
    public List<ProductResponseDTO> getProductsByCategory(String category) {
        return productRepository.findByCategory(category).stream()
                .map(this::toResponse)
                .toList();
    }

    private ProductResponseDTO toResponse(Product product) {
        ProductResponseDTO dto = mapper.map(product, ProductResponseDTO.class);
        if (product.getRestaurant() != null) {
            dto.setRestaurantId(product.getRestaurant().getId());
        }
        return dto;
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        productRepository.delete(product);
    }

    @Override
    public List<ProductResponseDTO> searchProductsByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return products.stream()
                .map(p -> mapper.map(p, ProductResponseDTO.class))
                .toList();
    }

    @Override
    public boolean isOwner(Long productId) {
        com.deliverytech.delivery_api.model.User currentUser = com.deliverytech.delivery_api.security.SecurityUtils.getCurrentUser();
        if (currentUser == null || currentUser.getRestaurantId() == null) {
            return false;
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        return product.getRestaurant().getId().equals(currentUser.getRestaurantId());
    }
}
