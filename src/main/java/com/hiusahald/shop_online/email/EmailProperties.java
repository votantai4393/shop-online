package com.hiusahald.shop_online.email;

import lombok.Builder;

@Builder
public record EmailProperties(
        String url,
        String username,
        String code,
        String newPassword
) {
}
