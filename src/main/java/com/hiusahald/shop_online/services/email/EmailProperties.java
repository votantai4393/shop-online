package com.hiusahald.shop_online.services.email;

import lombok.Builder;

@Builder
public record EmailProperties(
        String url,
        String username,
        String code,
        String newPassword
) {
}
