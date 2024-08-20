package com.hiusahald.shop_online.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record SignupForm(

        @NotBlank(message = "First name is required!")
        String firstname,

        @NotBlank(message = "Last name is required!")
        String lastname,

        @NotBlank(message = "Email is required!")
        @Email(message = "Please provide a valid email address! (e.g., example@gmail.com)")
        String email,

        @NotBlank(message = "Password is required!")
        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters!")
        String password,

        @NotBlank(message = "Confirm password is required!")
        @Size(min = 6, max = 20, message = "Confirm password must be between 6 and 20 characters!")
        String confirmPassword
) {
    public SignupForm {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Password and confirm password do not match!");
        }
    }

}
