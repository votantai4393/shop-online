package com.hiusahald.shop_online.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponse(
        Long id,
        @JsonProperty(namespace = "full_name")
        String fullName,
        String email,
        @JsonProperty(namespace = "is_admin")
        boolean isAdmin,
        @JsonProperty(namespace = "created_at")
        LocalDateTime createdAt
) {
}
