package com.hiusahald.shop_online.services.shop.category;

import com.hiusahald.shop_online.dto.request.CategoryDto;
import com.hiusahald.shop_online.dto.response.CategoryResponse;
import com.hiusahald.shop_online.dto.response.PageResponse;
import org.springframework.security.core.Authentication;

public interface CategoryService {

    CategoryResponse saveCategory(CategoryDto categoryDto, Authentication authentication);

    CategoryResponse updateCategory(Long id, CategoryDto categoryDto,
            Authentication authentication);

    void deleteCategory(Long id, Authentication authentication);

    CategoryResponse getCategory(Long id);

    PageResponse<CategoryResponse> getAllCategories(int pageNumber, int pageSize);

}
