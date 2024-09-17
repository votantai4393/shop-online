package com.hiusahald.shop_online.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank(message = "Name is required!")
        String name
) {
}
