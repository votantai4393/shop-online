package com.hiusahald.shop_online.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hiusahald.shop_online.dto.request.ProductRequest;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderItemDto(
       Long id,
       Integer quantity,
       ProductRequest product
) {
}
