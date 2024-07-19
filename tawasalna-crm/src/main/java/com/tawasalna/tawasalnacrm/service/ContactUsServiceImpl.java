package com.tawasalna.tawasalnacrm.service;

import com.tawasalna.tawasalnacrm.models.ContactUs;
import com.tawasalna.tawasalnacrm.repository.ContactUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactUsServiceImpl implements ContactUsService {

    private final ContactUsRepository contactUsRepository;

    @Autowired
    public ContactUsServiceImpl(ContactUsRepository contactUsRepository) {
        this.contactUsRepository = contactUsRepository;
    }

    @Override
    public ContactUs addContactUs(ContactUs contactUs) {
        return contactUsRepository.save(contactUs);
    }

    @Override
    public List<ContactUs> getAllContactUs() {
        return contactUsRepository.findAll();
    }

    @Override
    public Optional<ContactUs> getContactUsById(String id) {
        return contactUsRepository.findById(id);
    }


    @Override
    public ContactUs updateContactUs( ContactUs updatedContactUs) {


        return contactUsRepository.save(updatedContactUs);
    }

    @Override
    public void deleteContactUs(String id) {
        contactUsRepository.deleteById(id);
    }
    @Override
    public ContactUs getContactDetails() {
        // Fetch the contact details from the database dynamically
        List<ContactUs> contactDetailsList = contactUsRepository.findAll();

        // Check if any contact details exist
        if (!contactDetailsList.isEmpty()) {
            // Assuming you want the first contact details, you can get it from the list
            return contactDetailsList.get(0);
        } else {
            // If no contact details found, return null or handle the case appropriately
            return null;
        }
    }

    @Override
    public void archiveContactUs(String contactusId) {
        Optional<ContactUs> contactUsOptional = contactUsRepository.findById(contactusId);
        if (contactUsOptional.isPresent()) {
            ContactUs contactUs = contactUsOptional.get();
            // Set the archived field to true
            contactUs.setArchived(true);
            contactUsRepository.save(contactUs);
        }
    }
}
