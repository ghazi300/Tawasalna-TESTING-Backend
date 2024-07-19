package com.tawasalna.tawasalnacrm;


import com.tawasalna.tawasalnacrm.models.ContactUs;
import com.tawasalna.tawasalnacrm.repository.ContactUsRepository;
import com.tawasalna.tawasalnacrm.service.ContactUsService;
import com.tawasalna.tawasalnacrm.service.ContactUsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContactUsServiceImplTest {

    @Mock
    private ContactUsRepository contactUsRepository;

    private ContactUsService contactUsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        contactUsService = new ContactUsServiceImpl(contactUsRepository);
    }

    @Test
    void testAddContactUs() {
        ContactUs contactUs = new ContactUs();
        when(contactUsRepository.save(contactUs)).thenReturn(contactUs);

        ContactUs savedContactUs = contactUsService.addContactUs(contactUs);

        assertNotNull(savedContactUs);
        verify(contactUsRepository, times(1)).save(contactUs);
    }

    @Test
    void testGetAllContactUs() {
        List<ContactUs> contactUsList = new ArrayList<>();
        when(contactUsRepository.findAll()).thenReturn(contactUsList);

        List<ContactUs> retrievedContactUsList = contactUsService.getAllContactUs();

        assertNotNull(retrievedContactUsList);
        assertEquals(0, retrievedContactUsList.size());
        verify(contactUsRepository, times(1)).findAll();
    }

    @Test
    void testGetContactUsById() {
        String id = "1";
        ContactUs contactUs = new ContactUs();
        when(contactUsRepository.findById(id)).thenReturn(Optional.of(contactUs));

        Optional<ContactUs> retrievedContactUsOptional = contactUsService.getContactUsById(id);

        assertTrue(retrievedContactUsOptional.isPresent());
        assertEquals(contactUs, retrievedContactUsOptional.get());
        verify(contactUsRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateContactUs() {
        ContactUs updatedContactUs = new ContactUs();
        when(contactUsRepository.save(updatedContactUs)).thenReturn(updatedContactUs);

        ContactUs result = contactUsService.updateContactUs(updatedContactUs);

        assertNotNull(result);
        assertEquals(updatedContactUs, result);
        verify(contactUsRepository, times(1)).save(updatedContactUs);
    }

    @Test
    void testDeleteContactUs() {
        String id = "1";

        assertDoesNotThrow(() -> contactUsService.deleteContactUs(id));

        verify(contactUsRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetContactDetails() {
        List<ContactUs> contactDetailsList = new ArrayList<>();
        ContactUs contactDetails = new ContactUs();
        contactDetailsList.add(contactDetails);
        when(contactUsRepository.findAll()).thenReturn(contactDetailsList);

        ContactUs result = contactUsService.getContactDetails();

        assertNotNull(result);
        assertEquals(contactDetails, result);
        verify(contactUsRepository, times(1)).findAll();
    }

    @Test
    void testArchiveContactUs() {
        String contactusId = "1";
        ContactUs contactUs = new ContactUs();
        contactUs.setId(contactusId);
        when(contactUsRepository.findById(contactusId)).thenReturn(Optional.of(contactUs));
        when(contactUsRepository.save(contactUs)).thenReturn(contactUs);

        assertDoesNotThrow(() -> contactUsService.archiveContactUs(contactusId));

        assertTrue(contactUs.isArchived());
        verify(contactUsRepository, times(1)).findById(contactusId);
        verify(contactUsRepository, times(1)).save(contactUs);
    }
}
