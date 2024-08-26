package com.hiusahald.shop_online.services.email;

import com.hiusahald.shop_online.constants.EmailTemplate;

public interface EmailService {

    void send(String recipient, EmailTemplate template, EmailProperties properties);

}
