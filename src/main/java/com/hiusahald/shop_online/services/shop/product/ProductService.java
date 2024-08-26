package com.hiusahald.shop_online.services.shop.product;

import com.hiusahald.shop_online.dto.response.PageResponse;
import com.hiusahald.shop_online.dto.request.ProductDto;
import com.hiusahald.shop_online.dto.response.ProductResponse;
import org.springframework.security.core.Authentication;

public interface ProductService {

    ProductResponse getProduct(Long id);

    PageResponse<ProductResponse> getAllProduct(int number, int size);

    ProductResponse saveProduct(ProductDto request, Authentication authentication);

    ProductResponse updateProduct(Long id, ProductDto request, Authentication authentication);

    void deleteProduct(Long id, Authentication authentication);

    PageResponse<ProductResponse> search(String content, int number, int size);

}
