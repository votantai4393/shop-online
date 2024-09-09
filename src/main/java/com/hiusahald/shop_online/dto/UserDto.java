package com.hiusahald.shop_online.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(
        Long id,
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        Set<AddressDto> addresses,
        Set<PaymentDto> paymentHistory,
        Set<OrderDto> orderHistory,
        CartDto currentCart,
        Boolean isAdmin,
        Boolean enabled,
        Boolean banned,
        LocalDateTime cratedAt,
        LocalDateTime updatedAt
) {
}
