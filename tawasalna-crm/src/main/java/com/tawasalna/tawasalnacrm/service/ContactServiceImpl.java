package com.tawasalna.tawasalnacrm.service;

import com.tawasalna.tawasalnacrm.models.Contact;
import com.tawasalna.tawasalnacrm.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<Contact> getAllContact() {
        return contactRepository.findAll();
    }

    @Override
    public Contact getContactById(String id) {
        return contactRepository.findById(id).orElse(null);
    }

    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact updateContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public void deleteContact(String id) {
        contactRepository.deleteById(id);
    }

    @Override
    public void archiveContact(String contactId) {
        Optional<Contact> contactOptional = contactRepository.findById(contactId);
        if (contactOptional.isPresent()) {
            Contact contact = contactOptional.get();
            // Set the archived field to true
            contact.setArchived(true);
            contactRepository.save(contact);
        }
    }
}
