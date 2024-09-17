package com.hiusahald.shop_online.services.shop.product.category;

import com.hiusahald.shop_online.dto.CategoryDto;
import com.hiusahald.shop_online.dto.request.CategoryRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategories(Authentication auth);

    CategoryDto createCategory(String categoryName, Authentication auth);

    CategoryDto updateCategory(Long categoryId, CategoryRequest request, Authentication auth);

    void deleteCategory(Long categoryId, Authentication auth);
}
