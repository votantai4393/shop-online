package com.hiusahald.shop_online.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        @NotBlank(message = "Country is required!")
        String country,
        @NotBlank(message = "City is required!")
        String city,
        @NotBlank(message = "Postal Code is required!")
        String postalCode,
        @NotBlank(message = "Detail is required!")
        String detail,
        String department
) {
}
