package com.hiusahald.shop_online.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.Map;
import java.util.Set;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExceptionResponse(
        int businessCode,
        String businessMessage,
        String error,
        Map<String, String> errors,
        Set<String> validationErrors
) {
}
