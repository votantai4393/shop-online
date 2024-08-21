package com.hiusahald.shop_online.mail;

public record EMailTemplateProperties(
        String url,
        String username,
        String code,
        String newPassword
) {
}
