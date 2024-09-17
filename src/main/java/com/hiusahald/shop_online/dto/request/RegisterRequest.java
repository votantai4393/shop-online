package com.hiusahald.shop_online.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "First name is required!")
        @Size(max = 60, message = "First name can not more than 60 characters!")
        String firstname,
        @NotBlank(message = "Last name is required!")
        @Size(max = 60, message = "Last name can not more than 60 characters!")
        String lastname,
        @NotBlank(message = "Email is required!")
        @Email(message = "Please provide a valid email address! (e.g., example@gmail.com)")
        String email,
        @NotBlank(message = "Password is required!")
        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters!")
        String password
) {
}
