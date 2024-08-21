package com.hiusahald.shop_online.mail;

import lombok.Builder;

@Builder
public record EmailTemplateProperties(
        String url,
        String username,
        String token,
        String newPassword
) {
}
