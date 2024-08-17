package com.hiusahald.shop_online.services.email;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmail(String recipient, String subject, EmailTemplate template, FormEmailProperties props) throws MessagingException;

}
