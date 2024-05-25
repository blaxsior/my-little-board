package com.blaxsior.board.domain.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {
    @Value("${spring.mail.username}")
    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }
    private String fromEmail;

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    public void sendMail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendMail(String toEmail, String subject, String template, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);

        String body = templateEngine.process(template, context);
        this.sendMail(toEmail, subject, body);
    }
}
