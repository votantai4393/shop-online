package com.hiusahald.shop_online.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CartItemDto(
        Integer quantity,
        ProductDto product,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
