package com.hiusahald.shop_online.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Set;

@Builder
public record ExceptionResponse(
        HttpStatus businessCode,
        String businessMessage,
        Set<String> validationErrors,
        String messageError,
        Map<String, String> messageErrors
) {
}
