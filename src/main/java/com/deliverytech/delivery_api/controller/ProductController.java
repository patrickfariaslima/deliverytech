package com.deliverytech.delivery_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.dto.request.ProductDTO;
import com.deliverytech.delivery_api.dto.response.ProductResponseDTO;
import com.deliverytech.delivery_api.service.ProductService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/produtos")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getByProductId(@PathVariable Long id) {
        return ResponseEntity.status(200).body(productService.getProductById(id));
    }

    @PostMapping("/{restaurantId}")
    public ResponseEntity<ProductResponseDTO> createProduct(@PathVariable Long restaurantId, @RequestBody ProductDTO newProduct) {
        return ResponseEntity.status(201).body(productService.createProduct(restaurantId, newProduct));
    }

    @GetMapping("/restaurante/{restaurantId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.status(200).body(productService.getProductsByRestaurant(restaurantId));
    }
    
}
