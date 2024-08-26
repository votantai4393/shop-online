package com.hiusahald.shop_online.services.email;

import com.hiusahald.shop_online.constants.EmailTemplate;
import com.hiusahald.shop_online.exceptions.MailSendingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
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
    @Async
    public void send(String recipient, EmailTemplate template, EmailProperties props) {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper =
                    new MimeMessageHelper(
                            mimeMessage,
                            MimeMessageHelper.MULTIPART_MODE_MIXED,
                            StandardCharsets.UTF_8.name()
                    );
            mimeMessageHelper.setFrom(this.from);
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject(template.getSubject());
            mimeMessageHelper.setText(this.getContent(template, props), true);
            this.javaMailSender.send(mimeMessage);
        } catch (MessagingException | MailException e) {
            throw new MailSendingException("Email can't be sent", e);
        }
    }

    private String getContent(EmailTemplate template, EmailProperties props) {
        Context context = new Context();
        context.setVariables(this.getProperties(template, props));
        return this.templateEngine.process(template.getTemplate(), context);
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
            default -> new HashMap<>();
        };
    }

}
