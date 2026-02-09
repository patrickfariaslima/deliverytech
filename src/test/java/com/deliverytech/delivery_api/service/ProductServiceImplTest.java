package com.deliverytech.delivery_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.deliverytech.delivery_api.dto.request.ProductDTO;
import com.deliverytech.delivery_api.exceptions.BusinessException;
import com.deliverytech.delivery_api.exceptions.EntityNotFoundException;
import com.deliverytech.delivery_api.model.Product;
import com.deliverytech.delivery_api.model.Restaurant;
import com.deliverytech.delivery_api.repository.ProductRepository;
import com.deliverytech.delivery_api.repository.RestaurantRepository;
import com.deliverytech.delivery_api.service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProduct_shouldThrowWhenPriceInvalid() {
        ProductDTO dto = new ProductDTO();
        dto.setPrice(BigDecimal.ZERO);

        assertThrows(BusinessException.class, () -> productService.createProduct(dto));
    }

    @Test
    void createProduct_shouldThrowWhenRestaurantNotFound() {
        ProductDTO dto = new ProductDTO();
        dto.setPrice(new BigDecimal("10.00"));
        dto.setRestaurantId(1L);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.createProduct(dto));
    }

    @Test
    void getProductById_shouldThrowWhenNotAvailable() {
        Product product = new Product();
        product.setAvailable(false);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(BusinessException.class, () -> productService.getProductById(1L));
    }

    @Test
    void createProduct_shouldCreateWhenValid() {
        ProductDTO dto = new ProductDTO();
        dto.setPrice(new BigDecimal("10.00"));
        dto.setRestaurantId(1L);

        Restaurant restaurant = new Restaurant();
        Product product = new Product();

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(mapper.map(dto, Product.class)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(mapper.map(product, com.deliverytech.delivery_api.dto.response.ProductResponseDTO.class))
            .thenReturn(new com.deliverytech.delivery_api.dto.response.ProductResponseDTO());

        assertNotNull(productService.createProduct(dto));
        verify(productRepository).save(product);
    }
}