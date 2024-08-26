package com.hiusahald.shop_online.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailTemplate {

    ACTIVATE_ACCOUNT("activate_account", "activate your account now!"),
    RESET_PASSWORD("reset_password", "Your password has reset successfully!");

    private final String template;
    private final String subject;

}
