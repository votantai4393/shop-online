package com.hiusahald.shop_online.email;

import com.hiusahald.shop_online.constants.EmailTemplate;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    @Async
    void send(String recipient, EmailTemplate template, EmailProperties properties);

}
