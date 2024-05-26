package com.blaxsior.board.domain.mail;


import com.blaxsior.board.BoardApplication;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;

//@SpringBootTest
@SpringBootTest(
        classes = BoardApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestPropertySource(locations = {"classpath:/application-test.properties"})
//@ContextConfiguration(classes = BoardApplication.class)
public class MailServiceTest {

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("springboot", "springboot"))
            .withPerMethodLifecycle(false);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    private MailService mailService;
    @BeforeEach
    public void beforeEach() {
        mailService = new MailService(mailSender, templateEngine);
    }

    @Test
    void sendEmail() throws MessagingException {
        var toEmail = "test@test.com";
        var subject = "test subject";
        var body = "this is test";

        mailService.sendMail(toEmail, subject, body);
        greenMail.waitForIncomingEmail(1);

        var messages = greenMail.getReceivedMessages();
        var message = messages[0];
        var new_body = GreenMailUtil.getBody(message);

        assertThat(new_body).isEqualTo(body);
        assertThat(message.getAllRecipients().length).isEqualTo(1);
        assertThat(message.getSubject()).isEqualTo(subject);
    }

    @Test
    void sendTemplatedEmail() throws MessagingException {
        var toEmail = "test@test.com";
        var subject = "test subject";
        var testparam = "this is testparam";

        Map<String, Object> params = new HashMap<>();
        params.put("testparam", testparam);

        mailService.sendMail(toEmail, subject, "mail/test", params);

        var message = greenMail.getReceivedMessages()[0];

        var new_body = GreenMailUtil.getBody(message);
        // testparam 포함하는지?
        assertThat(new_body).contains(testparam);
        assertThat(message.getSubject()).isEqualTo(subject);
    }

    @TestConfiguration
    public static class MailConfiguration {
        @Bean
        public JavaMailSender getJavaMailSender() {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("127.0.0.1");
            mailSender.setPort(3025);

            mailSender.setUsername("springboot");
            mailSender.setPassword("springboot");

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");

            return mailSender;
        }
    }
}