package com.hiusahald.shop_online.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CartDto(
        Long id,
        UserDto userDto,
        List<CartItemDto> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
