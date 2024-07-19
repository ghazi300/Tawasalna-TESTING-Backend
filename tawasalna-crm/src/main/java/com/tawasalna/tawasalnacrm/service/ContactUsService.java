package com.tawasalna.tawasalnacrm.service;

import com.tawasalna.tawasalnacrm.models.ContactUs;

import java.util.List;
import java.util.Optional;

public interface ContactUsService {
    ContactUs addContactUs(ContactUs contactUs);
    List<ContactUs> getAllContactUs();
    Optional<ContactUs> getContactUsById(String id);
    ContactUs updateContactUs( ContactUs updatedContactUs);
    void deleteContactUs(String id);
    ContactUs getContactDetails();
    void archiveContactUs(String contactusId); // New m
    // ethod added


}
