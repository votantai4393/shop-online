package com.hiusahald.shop_online.dto;

import com.hiusahald.shop_online.dto.request.CategoryDto;
import com.hiusahald.shop_online.models.order.OrderItem;

import java.time.LocalDateTime;
import java.util.Set;

public record ProductDto(
        Long id,
        String name,
        Double price,
        String description,
        Integer inventory,
        ImageDto thumbnail,
        CategoryDto category,
        Set<ImageDto> images,
        Set<OrderItem> orderItems,
        Set<CartItemDto> cartItems,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
