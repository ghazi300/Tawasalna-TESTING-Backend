package com.example.residentsupportservices.Controlleur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@RestController
public class EmailController {

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/sendEmail")
    public void sendEventDetailsEmail(@RequestBody Map<String, Object> payload) throws MessagingException {
        String recipientEmail = (String) payload.get("recipientEmail");
        Map<String, Object> eventDetails = (Map<String, Object>) payload.get("eventDetails");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(recipientEmail);
        helper.setSubject("Détails de l'événement : " + eventDetails.get("title"));
        helper.setText("Détails de l'événement :\n\n" +
                "Titre : " + eventDetails.get("title") + "\n" +
                "Start : " + eventDetails.get("start") + "\n" +
                "End : " + eventDetails.get("end") + "\n" +
                "Location : " + eventDetails.get("location") + "\n" +
                "Description : " + eventDetails.get("description") + "\n" +
                "Category : " + eventDetails.get("category") + "\n" +
                "Max Participants : " + eventDetails.get("maxParticipants") + "\n" +
                "Notes : " + eventDetails.get("notes"));

        javaMailSender.send(message);
    }
}
