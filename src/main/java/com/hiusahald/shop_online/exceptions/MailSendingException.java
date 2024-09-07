package com.hiusahald.shop_online.exceptions;

public class MailSendingException extends RuntimeException {

    public MailSendingException() {
        super();
    }

    public MailSendingException(String message) {
        super(message);
    }

    public MailSendingException(String message, Throwable cause) {
        super(message, cause);
    }

}
