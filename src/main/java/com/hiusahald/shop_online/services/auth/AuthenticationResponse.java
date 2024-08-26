package com.hiusahald.shop_online.services.auth;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String token
) {
}
