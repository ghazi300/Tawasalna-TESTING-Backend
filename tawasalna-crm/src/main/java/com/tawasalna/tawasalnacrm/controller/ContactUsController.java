package com.tawasalna.tawasalnacrm.controller;

import com.tawasalna.tawasalnacrm.models.ContactUs;
import com.tawasalna.tawasalnacrm.service.ContactUsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/crm/contact-us")
@CrossOrigin("*")
@AllArgsConstructor
public class ContactUsController {

    private final ContactUsService contactUsService;


    @PostMapping("/save")
    @CrossOrigin("*")

    public ContactUs addContactUs(@RequestBody ContactUs contactUs) {
        return contactUsService.addContactUs(contactUs);
    }

    @GetMapping("/all")
    @CrossOrigin("*")

    public List<ContactUs> getAllContactUs() {
        return contactUsService.getAllContactUs();
    }

    @GetMapping("/{id}")
    @CrossOrigin("*")

    public ContactUs getContactUsById(@PathVariable String id) {
        Optional<ContactUs> contactUsOptional = contactUsService.getContactUsById(id);
        return contactUsOptional.orElse(null); // return null if contactUsOptional is empty
    }

    @PutMapping("/{id}")
    @CrossOrigin("*")

    public ContactUs updateContactUs( @RequestBody ContactUs updatedContactUs) {
        return contactUsService.updateContactUs( updatedContactUs);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin("*")

    public void deleteContactUs(@PathVariable String id) {
        contactUsService.deleteContactUs(id);
    }
    @GetMapping("/details")
    @CrossOrigin("*")

    public ResponseEntity<ContactUs> getContactDetails() {
        ContactUs contactDetails = contactUsService.getContactDetails();

        if (contactDetails != null) {
            return ResponseEntity.ok(contactDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/archive/{id}")
    @CrossOrigin("*")

    public ResponseEntity<?> archiveContactUs(@PathVariable String id) {
        contactUsService.archiveContactUs(String.valueOf(id));
        return ResponseEntity.ok().build();
    }

}

