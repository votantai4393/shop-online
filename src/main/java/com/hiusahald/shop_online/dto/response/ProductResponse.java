package com.hiusahald.shop_online.dto.response;

import com.hiusahald.shop_online.models.product.Category;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductResponse(
        Long id,
        String name,
        String description,
        Category category,
        Double price,
        String thumbnail,
        List<String> images
) {
}
