package com.hiusahald.shop_online.services.email;

import lombok.Getter;

@Getter
public enum EmailTemplate {
    ACTIVATE_ACCOUNT("activate_account");

    EmailTemplate(String name) {
        this.name = name;
    }

    private String name;
}
