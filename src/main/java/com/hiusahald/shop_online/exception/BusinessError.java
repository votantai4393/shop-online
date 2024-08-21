package com.hiusahald.shop_online.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BusinessError {

    NO_CODE(HttpStatus.NOT_IMPLEMENTED, "No code!"),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "Password doesn't match!"),
    USER_EXISTED(HttpStatus.BAD_REQUEST, "Email has existed already!"),
    ERROR_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "Server error!"),
    RESEND_EMAIL(HttpStatus.NOT_IMPLEMENTED, "An new email has been sent, please check your email!");

    private final HttpStatus code;
    private final String message;

}
