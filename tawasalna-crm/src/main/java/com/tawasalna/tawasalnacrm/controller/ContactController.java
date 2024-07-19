package com.tawasalna.tawasalnacrm.controller;

import com.tawasalna.tawasalnacrm.models.Contact;
import com.tawasalna.tawasalnacrm.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/all")
    public List<Contact> getAllContact() {
        return contactService.getAllContact();
    }

    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable String id) {
        return contactService.getContactById(id);
    }

    @PostMapping("/save")
    public Contact saveContact(@RequestBody Contact contact) {
        return contactService.saveContact(contact);
    }

    @PutMapping("/update")
    public Contact updateContact(@RequestBody Contact contact) {
        return contactService.updateContact(contact);
    }

    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable String id) {
        contactService.deleteContact(id);
    }

    @PostMapping("/archive/{id}")
    public ResponseEntity<Void> archiveContact(@PathVariable String id) {
        contactService.archiveContact(id);
        return ResponseEntity.ok().build();
    }
}
