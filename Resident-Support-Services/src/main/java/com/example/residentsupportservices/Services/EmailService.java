package com.example.residentsupportservices.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired

    private JavaMailSender javaMailSender;

    public void sendEventDetailsEmail(String recipientEmail, com.example.residentsupportservices.entity.Event event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Détails de l'événement : " + event.getTitle());
        message.setText("Détails de l'événement :\n\n" +
                "Titre : " + event.getTitle() + "\n" +
                "Start : " + event.getStart() + "\n" +
                "End : " + event.getEnd() + "\n" +
                "Location : " + event.getLocation() + "\n" +
                "Description : " + event.getDescription() + "\n" +
                "Category : " + event.getCategory() + "\n" +
                "Max Participants : " + event.getMaxParticipants() + "\n" +
                "Notes : " + event.getNotes());
        javaMailSender.send(message);
    }
}

