package com.hiusahald.shop_online.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginForm(
        @NotBlank(message = "Email is required!")
        @Email(message = "Please provide a valid email address! (e.g., example@gmail.com)")
        String email,
        @NotBlank(message = "Password is required!")
        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters!")
        String password
) {
}
