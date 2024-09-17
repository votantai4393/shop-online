package com.hiusahald.shop_online.dto.request;

import jakarta.validation.constraints.NotNull;

public record CartRequest(
        @NotNull(message = "Product id is required!")
        Long productId,
        @NotNull(message = "Quantity id is required!")
        Integer quantity
) {
}
