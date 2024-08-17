package com.hiusahald.shop_online.services.email;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String recipient, String subject, EmailTemplate template, FormEmailProperties props)
            throws MessagingException {
        var mimeMessage = javaMailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(recipient);
        helper.setSubject(subject);
        helper.setText(
                new TemplateEngine().process(
                        template.getName(),
                        new Context(
                                null,
                                Map.of(
                                        "url", props.url(),
                                        "code", props.code(),
                                        "username", props.username()
                                )
                        )
                ),
                true
        );
        javaMailSender.send(mimeMessage);
    }

}
