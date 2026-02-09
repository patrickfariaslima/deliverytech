package com.deliverytech.delivery_api.service.api;

import java.util.List;

import com.deliverytech.delivery_api.dto.request.ProductDTO;
import com.deliverytech.delivery_api.dto.response.ProductResponseDTO;

public interface ProductService {
    ProductResponseDTO createProduct(ProductDTO dto);
    List<ProductResponseDTO> getProductsByRestaurant(Long restaurantId);
    ProductResponseDTO getProductById(Long productId);
    ProductResponseDTO updateProduct(Long productId, ProductDTO dto);
    ProductResponseDTO setProductAvailability(Long productId, boolean available);
    List<ProductResponseDTO> getProductsByCategory(String category);
}
