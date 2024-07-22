package com.tawasalna.shared.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import java.util.List;

@Configuration
@Slf4j
public class EmailConfig {

    /*
    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Value("${spring.mail.port}")
    private Integer port;

    @Value("${spring.profiles.active}")
    private List<String> activeProfiles;

    @Value("${spring.mail.test-connection}")
    private boolean testConnectionOnStartup;

    @Bean
    public JavaMailSender getJavaMailSender() {
        final JavaMailSenderImpl mailer = new JavaMailSenderImpl();

        mailer.setProtocol(protocol);
        mailer.setUsername(username);
        mailer.setPassword(password);
        mailer.setHost(host);
        mailer.setPort(port);

        mailer.getJavaMailProperties().put("mail.debug", activeProfiles.contains("dev"));
        mailer.getJavaMailProperties().put("mail.smtp.ssl.protocols", "TLSv1.2");
        mailer.getJavaMailProperties().put("mail.smtp.connectiontimeout", 10_000);
        mailer.getJavaMailProperties().put("mail.smtp.timeout", 10_000);
        mailer.getJavaMailProperties().put("mail.smtp.writetimeout", 10_000);
        mailer.getJavaMailProperties().put("mail.smtp.auth", true);
        mailer.getJavaMailProperties().put("mail.smtp.starttls.required", true);
        mailer.getJavaMailProperties().put("mail.smtp.starttls.enable", true);

        if (testConnectionOnStartup)
            try {
                mailer.testConnection();
                log.info("Connected to mail host");
            } catch (MessagingException ex) {
                log.error("Cannot connect to mailing host.");
            }


        return mailer;
    }

     */
}
