package com.tawasalna.tawasalnacrm.service;

import com.tawasalna.tawasalnacrm.models.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> getAllContact();
    Contact getContactById(String id);
    Contact saveContact(Contact contact);
    Contact updateContact(Contact contact);
    void deleteContact(String id);
    void archiveContact(String contactId); // New method added

}


