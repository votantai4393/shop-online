package com.hiusahald.shop_online.authentication;

import lombok.Builder;

@Builder
public record AuthResponse(

        String token

) {
}
