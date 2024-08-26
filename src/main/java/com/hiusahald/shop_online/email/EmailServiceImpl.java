package com.hiusahald.shop_online.email;

import com.hiusahald.shop_online.constants.EmailTemplate;
import com.hiusahald.shop_online.exception.exceptions.EmailSendingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${app.mailing.from}")
    private final String from;

    @Override
    public void send(String recipient, EmailTemplate template, EmailProperties props) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED,
                    StandardCharsets.UTF_8.name()
            );
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject(template.getSubject());
            mimeMessageHelper.setText(getContent(template, props), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | MailException e) {
            throw new EmailSendingException("Email can't be sent", e);
        }
    }

    private String getContent(EmailTemplate template, EmailProperties props) {
        Context contextThymeleaf = new Context();
        contextThymeleaf.setVariables(getProperties(template, props));
        return templateEngine.process(template.getTemplate(), contextThymeleaf);
    }

    private Map<String, Object> getProperties(EmailTemplate template, EmailProperties props) {
        return switch (template) {
            case ACTIVATE_ACCOUNT -> Map.of(
                    "url", props.url(),
                    "username", props.username(),
                    "code", props.code()
            );
            case RESET_PASSWORD -> Map.of(
                    "url", props.url(),
                    "username", props.username(),
                    "new_password", props.newPassword()
            );
        };
    }

}
