package com.tawasalna.shared.mail;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@Slf4j
public class MailingServiceImpl implements IMailingService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public MailingServiceImpl(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    @Override
    public void sendEmail(String to, String subject, String templateName, @NotNull List<TemplateVariable> variables)
            throws MessagingException {
        deliverMail(to, subject, templateName, variables);
    }

    @Async
    @Override
    public void sendEmail(List<String> to, String subject, String templateName, @NotNull List<TemplateVariable> variables) throws MessagingException {
        deliverMail(to, subject, templateName, variables);
    }

    private void deliverMail(List<String> to, String sub, String templateName, @NotNull List<TemplateVariable> variables) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            helper.setFrom(from);
            helper.setTo(to.toArray(new String[0]));
            helper.setSubject(sub);
            final Context context = new Context();

            for (TemplateVariable variable : variables) {
                context.setVariable(variable.getName(), variable.getValue());
            }

            final String htmlContent = this.templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    private void deliverMail(String to, String sub, String templateName, @NotNull List<TemplateVariable> variables) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(sub);
            final Context context = new Context();

            for (TemplateVariable variable : variables) {
                context.setVariable(variable.getName(), variable.getValue());
            }

            final String htmlContent = this.templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void sendEmailtoMany(List<String> emails, String subject, String templateName, List<TemplateVariable> variables) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            // Convert the list of email addresses to an array of strings
            String[] recipients = new String[emails.size()];
            recipients = emails.toArray(recipients);

            helper.setTo(recipients); // Set multiple recipients
            helper.setFrom(from);
            helper.setSubject(subject);

            final Context context = new Context();

            variables.forEach(variable -> context.setVariable(variable.getName(), variable.getValue()));

            final String htmlContent = this.templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

}
