package com.hiusahald.shop_online.mail;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

@Async
public interface EmailService {

    void send(String recipient, EmailTemplate template,
            EmailTemplateProperties properties) throws MessagingException;

}
