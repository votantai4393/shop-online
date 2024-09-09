package com.hiusahald.shop_online.exceptions;

public class AccountUnverifiedException extends RuntimeException {

    public AccountUnverifiedException() {
        super();
    }

    public AccountUnverifiedException(String message) {
        super(message);
    }

}
