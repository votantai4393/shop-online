package com.hiusahald.shop_online.mail;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${app.mailing.from}")
    private String from;

    @Override
    public void send(String recipient, EmailTemplate template,
            EmailTemplateProperties props) throws MessagingException {
        var mimeMessage = javaMailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(recipient);
        mimeMessageHelper.setSubject(template.getSubject());
        mimeMessageHelper.setText(getContent(template, props), true);
        javaMailSender.send(mimeMessage);
    }

    private String getContent(EmailTemplate template, EmailTemplateProperties props) {
        var context = new Context();
        context.setVariables(getProperties(template, props));
        return templateEngine.process(template.getTemplate(), context);
    }

    private Map<String, Object> getProperties(EmailTemplate template, EmailTemplateProperties props) {
        return switch (template) {
            case ACTIVATE_ACCOUNT -> Map.of(
                    "url", props.url(),
                    "username", props.username(),
                    "token", props.token()
            );
            case RESET_PASSWORD -> Map.of(
                    "url", props.url(),
                    "username", props.username(),
                    "new_password", props.newPassword()
            );
            default -> new HashMap<>();
        };
    }
}
