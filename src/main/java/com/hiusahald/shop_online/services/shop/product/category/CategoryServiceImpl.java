package com.hiusahald.shop_online.services.shop.product.category;

import com.hiusahald.shop_online.dto.CategoryDto;
import com.hiusahald.shop_online.dto.request.CategoryRequest;
import com.hiusahald.shop_online.models.product.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategories(Authentication auth) {
        return List.of();
    }

    @Override
    public CategoryDto createCategory(String categoryName, Authentication auth) {
        return null;
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryRequest request, Authentication auth) {
        return null;
    }

    @Override
    public void deleteCategory(Long categoryId, Authentication auth) {

    }

}
