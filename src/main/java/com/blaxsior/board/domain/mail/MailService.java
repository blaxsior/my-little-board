package com.blaxsior.board.domain.mail;

import com.blaxsior.board.domain.mail.exception.MailSendFailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
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

    @Async
    public void sendMail(String toEmail, String subject, String body)  {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);
        } catch (MessagingException e) {
            throw new MailSendFailException("메일 전송에 실패했습니다.");
        }

        mailSender.send(message);
    }

    public void sendMail(String toEmail, String subject, String template, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);

        String body = templateEngine.process(template, context);
        this.sendMail(toEmail, subject, body);
    }
}
