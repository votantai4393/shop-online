package com.hiusahald.shop_online.mail;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

@Async
public interface EMailService {

    void send(String recipient, EmailTemplate template,
            EMailTemplateProperties properties) throws MessagingException;

}
