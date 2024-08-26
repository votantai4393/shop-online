package com.hiusahald.shop_online.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Set;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExceptionResponse(
        @JsonProperty(namespace = "business_code")
        HttpStatus businessCode,
        @JsonProperty(namespace = "business_message")
        String businessMessage,
        String error,
        Map<String, String> errors,
        @JsonProperty(namespace = "validation_errors")
        Set<String> validationErrors
) {
}
