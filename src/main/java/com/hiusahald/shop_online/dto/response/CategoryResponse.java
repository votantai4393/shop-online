package com.hiusahald.shop_online.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CategoryResponse(
        Long id,
        String name,
        @JsonProperty(namespace = "created_at")
        LocalDateTime createdAt
) {
}
