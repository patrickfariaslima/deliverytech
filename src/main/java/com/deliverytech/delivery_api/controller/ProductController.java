package com.deliverytech.delivery_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.dto.request.ProductDTO;
import com.deliverytech.delivery_api.dto.response.ProductResponseDTO;
import com.deliverytech.delivery_api.service.api.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Endpoints de produtos")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar produto")
    @ApiResponse(responseCode = "201", description = "Produto criado")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductDTO product) {
        return ResponseEntity.status(201).body(productService.createProduct(product));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    @ApiResponse(responseCode = "200", description = "Produto encontrado")
    public ResponseEntity<ProductResponseDTO> getByProductId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto")
    @ApiResponse(responseCode = "200", description = "Produto atualizado")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @PatchMapping("/{id}/disponibilidade")
    @Operation(summary = "Alterar disponibilidade do produto")
    @ApiResponse(responseCode = "200", description = "Disponibilidade alterada")
    public ResponseEntity<ProductResponseDTO> setProductAvailability(@PathVariable Long id, @RequestParam boolean available) {
        return ResponseEntity.ok(productService.setProductAvailability(id, available));
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Buscar produtos por categoria")
    @ApiResponse(responseCode = "200", description = "Lista de produtos")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable String categoria) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoria));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Remover produto",
        description = "Remove um produto do sistema. Não é possível remover produtos que já foram pedidos."
    )
    @ApiResponse(responseCode = "204", description = "Produto removido com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @ApiResponse(responseCode = "409", description = "Produto não pode ser removido - existe em pedidos")
    public ResponseEntity<Void> deleteProduct(
        @Parameter(description = "ID do produto", example = "1", required = true)
        @PathVariable Long id
    ) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @Operation(
        summary = "Buscar produtos por nome",
        description = "Realiza busca textual de produtos pelo nome (case-insensitive)"
    )
    @ApiResponse(responseCode = "200", description = "Lista de produtos encontrados")
    public ResponseEntity<List<ProductResponseDTO>> searchProductsByName(
        @Parameter(description = "Nome ou parte do nome do produto", example = "Pizza", required = true)
        @RequestParam String nome
    ) {
        return ResponseEntity.ok(productService.searchProductsByName(nome));
    }
}
