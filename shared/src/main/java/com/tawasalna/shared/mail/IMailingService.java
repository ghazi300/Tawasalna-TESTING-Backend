package com.tawasalna.shared.mail;

import javax.mail.MessagingException;
import java.util.List;

public interface IMailingService {

    /**
     * Sends a templated HTML email to the destination address.
     * @param email destination address
     * @param subject subject of the email
     * @param templateName file name of the Thymeleaf template to use
     * @param variables list of variables to add to the template, can be empty.
     * @throws MessagingException if any expected thing happened within the code
     */
    void sendEmail(String email, String subject, String templateName, List<TemplateVariable> variables) throws MessagingException;

     /**
     * Sends a templated HTML email to the destination addresses.
     * @param emails destination addresses
     * @param subject subject of the email
     * @param templateName file name of the Thymeleaf template to use
     * @param variables list of variables to add to the template, can be empty.
     * @throws MessagingException if any expected thing happened within the code
     */
    void sendEmailtoMany(List<String> emails, String subject, String templateName, List<TemplateVariable> variables) throws MessagingException;

    /**
     * Sends a templated HTML email to the destination addresses.
     * @param emails destination addresses
     * @param subject subject of the email
     * @param templateName file name of the Thymeleaf template to use
     * @param variables list of variables to add to the template, can be empty.
     * @throws MessagingException if any expected thing happened within the code
     */
    void sendEmail(List<String> emails, String subject, String templateName, List<TemplateVariable> variables) throws MessagingException;
}
