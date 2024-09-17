package com.hiusahald.shop_online.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessError {

    NO_CODE(1, "No code!"),
    BAD_CREDENTIAL(2, "Email or password was incorrect!"),
    LOCKED_ACCOUNT(3, "Account currently banned, please contact to admin to be supported!"),
    DISABLE_ACCOUNT(4, "Account has not been activated, please check your email to activate account!"),
    USER_EXISTED(5, "Email has existed already!"),
    ERROR_SERVER(100, "Something went wrong!");

    private final int code;
    private final String message;

}
