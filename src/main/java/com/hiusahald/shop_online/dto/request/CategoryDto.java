package com.hiusahald.shop_online.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(
        @NotBlank(message = "The description is required!")
        String name
) {
}
