package com.hiusahald.shop_online.services.shop.product;

import com.hiusahald.shop_online.dto.response.PageResponse;
import com.hiusahald.shop_online.dto.ProductDto;
import com.hiusahald.shop_online.dto.request.ProductRequest;
import org.springframework.security.core.Authentication;

public interface ProductService {

    ProductDto addProduct(ProductRequest request, Authentication auth);

    ProductDto updateProduct(Long productId, ProductRequest request, Authentication auth);

    void deleteProduct(Long productId, Authentication auth);

    ProductDto getProduct(Long productId, Authentication auth);

    PageResponse<ProductDto> getAllProducts(int number, int size);

    PageResponse<ProductDto> searchProduct(
            int number, int size, String content, String sortDirection, String sortBy, Long categoryId);

}
