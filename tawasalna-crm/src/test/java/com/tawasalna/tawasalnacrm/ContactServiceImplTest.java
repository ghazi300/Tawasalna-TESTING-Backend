package com.tawasalna.tawasalnacrm;

import com.tawasalna.tawasalnacrm.models.Contact;
import com.tawasalna.tawasalnacrm.repository.ContactRepository;
import com.tawasalna.tawasalnacrm.service.ContactServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ContactServiceImplTest {

    // Mock du repository Contact
    @Mock
    private ContactRepository contactRepository;

    // Classe à tester, avec les dépendances mockées injectées
    @InjectMocks
    private ContactServiceImpl contactService;

    // Méthode exécutée avant chaque test, initialise les mocks
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // Test de la méthode getAllContact
    @Test
    void testGetAllContact() {
        // Given
        List<Contact> contactList = new ArrayList<>();
        contactList.add(new Contact());
        contactList.add(new Contact());
        when(contactRepository.findAll()).thenReturn(contactList);

        // When
        List<Contact> result = contactService.getAllContact();

        // Then
        assertEquals(2, result.size(), "Le nombre de contacts retournés devrait être 2");
    }

    // Test de la méthode getContactById
    @Test
    void testGetContactById() {
        // Given
        Contact contact = new Contact();
        when(contactRepository.findById("1")).thenReturn(Optional.of(contact));

        // When
        Contact result = contactService.getContactById("1");

        // Then
        assertEquals(contact, result, "Le contact retourné devrait être celui avec l'ID '1'");
    }

    // Test de la méthode saveContact
    @Test
    void testSaveContact() {
        // Given
        Contact contact = new Contact();
        when(contactRepository.save(contact)).thenReturn(contact);

        // When
        Contact result = contactService.saveContact(contact);

        // Then
        assertEquals(contact, result, "Le contact retourné devrait être le même que celui en entrée");
    }

    @Test
    void testUpdateContact() {
        // Given
        Contact contact = new Contact();
        when(contactRepository.save(contact)).thenReturn(contact);

        // When
        Contact result = contactService.updateContact(contact);

        // Then
        assertEquals(contact, result, "Le contact retourné devrait être le même que celui en entrée");
    }

    // Test de la méthode deleteContact
    @Test
    void testDeleteContact() {
        // When
        contactService.deleteContact("1");

        // Then
        verify(contactRepository, times(1)).deleteById("1");
    }

    // Test de la méthode archiveContact
    @Test
    void testArchiveContact() {
        // Given
        Contact contact = new Contact();
        when(contactRepository.findById("1")).thenReturn(Optional.of(contact));

        // When
        contactService.archiveContact("1");

        // Then
        assertEquals(true, contact.isArchived(), "Le contact devrait être archivé après l'appel de la méthode");
        verify(contactRepository, times(1)).save(contact);
    }
}
