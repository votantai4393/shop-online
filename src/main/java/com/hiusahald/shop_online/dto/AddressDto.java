package com.hiusahald.shop_online.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AddressDto(
        Long id,
        String country,
        String city,
        String postalCode,
        String detail,
        String department,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
