package com.hiusahald.shop_online.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record OrderDto(
        Long id,
        String status,
        PaymentDto paymentDto,
        Set<OrderItemDto> orderItems,
        UserDto user,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
