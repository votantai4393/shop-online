package com.hiusahald.shop_online.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductDto(
        Long id,
        String name,
        Double price,
        String description,
        Integer inventory,
        CategoryDto category,
        String thumbnailUrl,
        List<String> imageUrls,
        Integer sold,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
