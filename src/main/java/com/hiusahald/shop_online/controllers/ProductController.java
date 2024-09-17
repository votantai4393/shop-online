package com.hiusahald.shop_online.controllers;

import com.hiusahald.shop_online.dto.response.PageResponse;
import com.hiusahald.shop_online.dto.ProductDto;
import com.hiusahald.shop_online.dto.request.ProductRequest;
import com.hiusahald.shop_online.services.shop.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(Authentication auth, @PathVariable("id") Long productId) {
        return ResponseEntity.ok(productService.getProduct(productId, auth));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductDto>> getAllProducts(
            @RequestParam(name = "page_number", defaultValue = "0") int pageNumber,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(productService.getAllProducts(pageNumber, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<ProductDto>> searchProduct(
            @RequestParam(name = "page_number", defaultValue = "0") int number,
            @RequestParam(name = "page_size", defaultValue = "10") int size,
            @RequestParam(name = "sort_direction", defaultValue = "desc") String sortDirection,
            @RequestParam(name = "sort_by", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "content", defaultValue = "") String content,
            @RequestParam(name = "category_id", defaultValue = "") Long categoryId) {
        return ResponseEntity.ok(
                productService.searchProduct(number, size, content, sortDirection, sortBy, categoryId));
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(Authentication auth,
            @RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(this.productService.addProduct(productRequest, auth));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(Authentication auth,
            @PathVariable("id") Long productId, @RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(this.productService.updateProduct(productId, productRequest, auth));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(Authentication auth, @PathVariable("id") Long productId) {
        productService.deleteProduct(productId, auth);
        return ResponseEntity.accepted().build();
    }


}
