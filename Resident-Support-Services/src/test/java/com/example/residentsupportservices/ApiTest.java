package com.example.residentsupportservices;

import com.example.residentsupportservices.Services.IChildcareProgramService;
import com.example.residentsupportservices.Services.IPetService;
import com.example.residentsupportservices.Services.IVaccinationService;
import com.example.residentsupportservices.entity.Event;
import com.example.residentsupportservices.services.*;
import org.apache.http.client.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.residentsupportservices.entity.Participant;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import com.example.residentsupportservices.Entity.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEventService eventService;
    @MockBean
    private com.example.residentsupportservices.services.IParticipantService participantService;
    @MockBean
    private IPetService petService;
    @MockBean
    private IVaccinationService vaccinationService;
    @MockBean
    private IChildcareProgramService childcareProgramService;



    // __________ test for Event _____________________________________________


    @Test
    void createEvent_createsEvent() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.of(2024, 8, 17, 13, 42, 40);
        Event event = new Event("66c0aa99f1c0506576ccee5d", "Event 1", startDateTime, startDateTime, new ArrayList<>(), "Location 1", "Description 1", "Category 1", "http://example.com/image1.jpg", 100, "Notes 1");

        when(eventService.createEvent(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Event 1\",\"start\":\"2024-08-17T13:42:40\",\"end\":\"2024-08-17T13:42:40\",\"location\":\"Location 1\",\"description\":\"Description 1\",\"category\":\"Category 1\",\"imageUrl\":\"http://example.com/image1.jpg\",\"maxParticipants\":100,\"notes\":\"Notes 1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"66c0aa99f1c0506576ccee5d\",\"title\":\"Event 1\",\"start\":\"2024-08-17T13:42:40\",\"end\":\"2024-08-17T13:42:40\",\"participants\":[],\"location\":\"Location 1\",\"description\":\"Description 1\",\"category\":\"Category 1\",\"imageUrl\":\"http://example.com/image1.jpg\",\"maxParticipants\":100,\"notes\":\"Notes 1\"}"));
    }
    @Test
    void getEventById_returnsEvent() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.of(2024, 8, 17, 13, 42, 40);
        Event event = new Event("66c0aa99f1c0506576ccee5d", "Event 1", startDateTime, startDateTime, new ArrayList<>(), "Location 1", "Description 1", "Category 1", "http://example.com/image1.jpg", 100, "Notes 1");

        when(eventService.getEventById("66c0aa99f1c0506576ccee5d")).thenReturn(event);

        mockMvc.perform(get("/api/events/66c0aa99f1c0506576ccee5d")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"66c0aa99f1c0506576ccee5d\",\"title\":\"Event 1\",\"start\":\"2024-08-17T13:42:40\",\"end\":\"2024-08-17T13:42:40\",\"participants\":[],\"location\":\"Location 1\",\"description\":\"Description 1\",\"category\":\"Category 1\",\"imageUrl\":\"http://example.com/image1.jpg\",\"maxParticipants\":100,\"notes\":\"Notes 1\"}"));
    }
    @Test
    void updateEvent_updatesEvent() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.of(2024, 8, 17, 13, 42, 40);
        Event updatedEvent = new Event("66c0aa99f1c0506576ccee5d", "Updated Event", startDateTime, startDateTime, new ArrayList<>(), "Updated Location", "Updated Description", "Updated Category", "http://example.com/updated-image.jpg", 200, "Updated Notes");

        when(eventService.updateEvent(any(String.class), any(Event.class))).thenReturn(updatedEvent);

        mockMvc.perform(put("/api/events/66c0aa99f1c0506576ccee5d")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Event\",\"start\":\"2024-08-17T13:42:40\",\"end\":\"2024-08-17T13:42:40\",\"location\":\"Updated Location\",\"description\":\"Updated Description\",\"category\":\"Updated Category\",\"imageUrl\":\"http://example.com/updated-image.jpg\",\"maxParticipants\":200,\"notes\":\"Updated Notes\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"66c0aa99f1c0506576ccee5d\",\"title\":\"Updated Event\",\"start\":\"2024-08-17T13:42:40\",\"end\":\"2024-08-17T13:42:40\",\"participants\":[],\"location\":\"Updated Location\",\"description\":\"Updated Description\",\"category\":\"Updated Category\",\"imageUrl\":\"http://example.com/updated-image.jpg\",\"maxParticipants\":200,\"notes\":\"Updated Notes\"}"));
    }

    @Test
    void getAllEvents_returnsEvents() throws Exception {
        // Mock event data
        LocalDateTime startDateTime = LocalDateTime.of(2024, 8, 17, 13, 42, 40);
        Event event1 = new Event("66c0aa99f1c0506576ccee5d", "Event 1", startDateTime, startDateTime, new ArrayList<>(), "Location 1", "Description 1", "Category 1", "http://example.com/image1.jpg", 100, "Notes 1");
        Event event2 = new Event("76c0aa99f1c0506576ccee5e", "Event 2", startDateTime, startDateTime, new ArrayList<>(), "Location 2", "Description 2", "Category 2", "http://example.com/image2.jpg", 150, "Notes 2");

        // Mock the service call
        when(eventService.getAllEvents()).thenReturn(Arrays.asList(event1, event2));

        mockMvc.perform(get("/api/events")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"66c0aa99f1c0506576ccee5d\",\"title\":\"Event 1\",\"start\":\"2024-08-17T13:42:40\",\"end\":\"2024-08-17T13:42:40\",\"participants\":[],\"location\":\"Location 1\",\"description\":\"Description 1\",\"category\":\"Category 1\",\"imageUrl\":\"http://example.com/image1.jpg\",\"maxParticipants\":100,\"notes\":\"Notes 1\"},{\"id\":\"76c0aa99f1c0506576ccee5e\",\"title\":\"Event 2\",\"start\":\"2024-08-17T13:42:40\",\"end\":\"2024-08-17T13:42:40\",\"participants\":[],\"location\":\"Location 2\",\"description\":\"Description 2\",\"category\":\"Category 2\",\"imageUrl\":\"http://example.com/image2.jpg\",\"maxParticipants\":150,\"notes\":\"Notes 2\"}]"));
    }


    @Test
    void deleteEvent_deletesEvent() throws Exception {
        // Configurez le comportement du service mocké pour la suppression
        doNothing().when(eventService).deleteEvent(anyString());

        // Effectuez la requête DELETE et vérifiez la réponse
        mockMvc.perform(delete("/api/events/66c0aa99f1c0506576ccee5d")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Vérifiez que le statut est 200 OK
    }

    // __________ test for Participent _____________________________________________

    @Test
    void createParticipant_createsParticipant() throws Exception {
        // Préparer les données
        Participant participant = new Participant(null, "John Doe", true, 30, "john@example.com", "1234567890", "123 Street", "None");
        Participant createdParticipant = new Participant("1", "John Doe", true, 30, "john@example.com", "1234567890", "123 Street", "None");

        // Configurer le service mocké
        when(participantService.createParticipant(any(Participant.class))).thenReturn(createdParticipant);

        // Effectuer la requête POST et vérifier la réponse
        mockMvc.perform(post("/api/participants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"attended\":true,\"age\":30,\"email\":\"john@example.com\",\"phone\":\"1234567890\",\"address\":\"123 Street\",\"specialNeeds\":\"None\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"name\":\"John Doe\",\"attended\":true,\"age\":30,\"email\":\"john@example.com\",\"phone\":\"1234567890\",\"address\":\"123 Street\",\"specialNeeds\":\"None\"}"));
    }

    @Test
    void getAllParticipants_returnsParticipants() throws Exception {
        // Préparer les données
        Participant participant1 = new Participant("1", "John Doe", true, 30, "john@example.com", "1234567890", "123 Street", "None");
        Participant participant2 = new Participant("2", "Jane Doe", false, 25, "jane@example.com", "0987654321", "456 Avenue", "None");
        List<Participant> participants = Arrays.asList(participant1, participant2);

        // Configurer le service mocké
        when(participantService.getAllParticipants()).thenReturn(participants);

        // Effectuer la requête GET et vérifier la réponse
        mockMvc.perform(get("/api/participants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"1\",\"name\":\"John Doe\",\"attended\":true,\"age\":30,\"email\":\"john@example.com\",\"phone\":\"1234567890\",\"address\":\"123 Street\",\"specialNeeds\":\"None\"},{\"id\":\"2\",\"name\":\"Jane Doe\",\"attended\":false,\"age\":25,\"email\":\"jane@example.com\",\"phone\":\"0987654321\",\"address\":\"456 Avenue\",\"specialNeeds\":\"None\"}]"));
    }
    @Test
    void getParticipantById_returnsParticipant() throws Exception {
        // Préparer les données
        Participant participant = new Participant("1", "John Doe", true, 30, "john@example.com", "1234567890", "123 Street", "None");

        // Configurer le service mocké
        when(participantService.getParticipantById("1")).thenReturn(participant);

        // Effectuer la requête GET et vérifier la réponse
        mockMvc.perform(get("/api/participants/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"name\":\"John Doe\",\"attended\":true,\"age\":30,\"email\":\"john@example.com\",\"phone\":\"1234567890\",\"address\":\"123 Street\",\"specialNeeds\":\"None\"}"));
    }
    @Test
    void updateParticipant_updatesParticipant() throws Exception {
        // Préparer les données
        Participant updatedParticipant = new Participant("1", "John Doe", false, 31, "john.doe@example.com", "1234567890", "123 Street", "None");

        // Configurer le service mocké
        when(participantService.updateParticipant(anyString(), any(Participant.class))).thenReturn(updatedParticipant);

        // Effectuer la requête PUT et vérifier la réponse
        mockMvc.perform(put("/api/participants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"attended\":false,\"age\":31,\"email\":\"john.doe@example.com\",\"phone\":\"1234567890\",\"address\":\"123 Street\",\"specialNeeds\":\"None\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"name\":\"John Doe\",\"attended\":false,\"age\":31,\"email\":\"john.doe@example.com\",\"phone\":\"1234567890\",\"address\":\"123 Street\",\"specialNeeds\":\"None\"}"));
    }


    @Test
    void deleteParticipant_deletesParticipant() throws Exception {
        // Configurer le service mocké pour la suppression
        doNothing().when(participantService).deleteParticipant(anyString());

        // Effectuer la requête DELETE et vérifier la réponse
        mockMvc.perform(delete("/api/participants/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Vérifiez que le statut est 200 OK
    }


    // __________ test for pet _____________________________________________

    @Test
    void addPet_createsPet() throws Exception {
        // Préparer les données
        Pet pet = new Pet(null, "Buddy", "Dog", "Golden Retriever", new Date(), "http://example.com/image.jpg");
        Pet createdPet = new Pet("1", "Buddy", "Dog", "Golden Retriever", new Date(), "http://example.com/image.jpg");

        // Configurer le service mocké
        when(petService.addPet(any(Pet.class))).thenReturn(createdPet);

        // Effectuer la requête POST et vérifier la réponse
        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Buddy\",\"type\":\"Dog\",\"breed\":\"Golden Retriever\",\"imageUrl\":\"http://example.com/image.jpg\"}")) // Removed birthDate
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"name\":\"Buddy\",\"type\":\"Dog\",\"breed\":\"Golden Retriever\",\"imageUrl\":\"http://example.com/image.jpg\"}", false)); // Removed birthDate
    }

    @Test
    void updatePet_updatesPet() throws Exception {
        // Prepare data
        Pet updatedPet = new Pet("1", "Buddy", "Dog", "Golden Retriever", new Date(), "http://example.com/new-image.jpg");

        // Mock the service
        when(petService.updatePet(anyString(), any(Pet.class))).thenReturn(updatedPet);

        // Perform the PUT request and verify the response
        mockMvc.perform(put("/api/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Buddy\",\"type\":\"Dog\",\"breed\":\"Golden Retriever\",\"imageUrl\":\"http://example.com/new-image.jpg\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"name\":\"Buddy\",\"type\":\"Dog\",\"breed\":\"Golden Retriever\",\"imageUrl\":\"http://example.com/new-image.jpg\"}"));
    }

    @Test
    void deletePet_deletesPet() throws Exception {
        // Configurer le service mocké pour la suppression
        doNothing().when(petService).deletePet(anyString());

        // Effectuer la requête DELETE et vérifier la réponse
        mockMvc.perform(delete("/api/pets/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Vérifiez que le statut est 204 No Content
    }
    @Test
    void getPetById_returnsPet() throws Exception {
        // Prepare data
        Pet pet = new Pet("1", "Buddy", "Dog", "Golden Retriever", new SimpleDateFormat("yyyy-MM-dd").parse("2024-08-17"), "http://example.com/image.jpg");

        // Mock the service
        when(petService.getPetById("1")).thenReturn(pet);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/pets/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"name\":\"Buddy\",\"type\":\"Dog\",\"breed\":\"Golden Retriever\",\"imageUrl\":\"http://example.com/image.jpg\"}"));
    }

    @Test
    void getAllPets_returnsPets() throws Exception {
        // Préparer les données
        Pet pet1 = new Pet("1", "Buddy", "Dog", "Golden Retriever", new Date(), "http://example.com/image1.jpg");
        Pet pet2 = new Pet("2", "Max", "Cat", "Siamese", new Date(), "http://example.com/image2.jpg");
        List<Pet> pets = Arrays.asList(pet1, pet2);

        // Configurer le service mocké
        when(petService.getAllPets()).thenReturn(pets);

        // Effectuer la requête GET et vérifier la réponse
        mockMvc.perform(get("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"1\",\"name\":\"Buddy\",\"type\":\"Dog\",\"breed\":\"Golden Retriever\",\"imageUrl\":\"http://example.com/image1.jpg\"},{\"id\":\"2\",\"name\":\"Max\",\"type\":\"Cat\",\"breed\":\"Siamese\",\"imageUrl\":\"http://example.com/image2.jpg\"}]"));
    }

    // __________ test for Vaccination  _____________________________________________


    @Test
    void addVaccination_createsVaccination() throws Exception {
        Vaccination vaccination = new Vaccination(null, "1", "Rabies", LocalDate.of(2023, 8, 19), LocalDate.of(2024, 8, 19), "completed");
        Vaccination createdVaccination = new Vaccination("1", "1", "Rabies", LocalDate.of(2023, 8, 19), LocalDate.of(2024, 8, 19), "completed");

        when(vaccinationService.addVaccination(any(Vaccination.class))).thenReturn(createdVaccination);

        mockMvc.perform(post("/api/vaccinations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"petId\":\"1\",\"vaccinationName\":\"Rabies\",\"vaccinationDate\":\"2023-08-19\",\"nextDueDate\":\"2024-08-19\",\"status\":\"completed\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"petId\":\"1\",\"vaccinationName\":\"Rabies\",\"vaccinationDate\":\"2023-08-19\",\"nextDueDate\":\"2024-08-19\",\"status\":\"completed\"}"));
    }
    @Test
    void updateVaccination_updatesVaccination() throws Exception {
        Vaccination updatedVaccination = new Vaccination("1", "1", "Rabies", LocalDate.of(2023, 8, 19), LocalDate.of(2024, 8, 19), "completed");
        when(vaccinationService.updateVaccination(anyString(), any(Vaccination.class))).thenReturn(updatedVaccination);

        mockMvc.perform(put("/api/vaccinations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"petId\":\"1\",\"vaccinationName\":\"Rabies\",\"vaccinationDate\":\"2023-08-19\",\"nextDueDate\":\"2024-08-19\",\"status\":\"completed\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"petId\":\"1\",\"vaccinationName\":\"Rabies\",\"vaccinationDate\":\"2023-08-19\",\"nextDueDate\":\"2024-08-19\",\"status\":\"completed\"}"));
    }

    @Test
    void getVaccinationById_returnsVaccination() throws Exception {
        Vaccination vaccination = new Vaccination("1", "1", "Rabies", LocalDate.of(2023, 8, 19), LocalDate.of(2024, 8, 19), "completed");

        when(vaccinationService.getVaccinationById("1")).thenReturn(Optional.of(vaccination));

        mockMvc.perform(get("/api/vaccinations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"petId\":\"1\",\"vaccinationName\":\"Rabies\",\"vaccinationDate\":\"2023-08-19\",\"nextDueDate\":\"2024-08-19\",\"status\":\"completed\"}"));
    }

    @Test
    void getAllVaccinations_returnsVaccinations() throws Exception {
        Vaccination vaccination1 = new Vaccination("1", "1", "Rabies", LocalDate.of(2023, 8, 19), LocalDate.of(2024, 8, 19), "completed");
        Vaccination vaccination2 = new Vaccination("2", "2", "Distemper", LocalDate.of(2023, 7, 10), LocalDate.of(2024, 7, 10), "due");
        List<Vaccination> vaccinations = Arrays.asList(vaccination1, vaccination2);

        when(vaccinationService.getAllVaccinations()).thenReturn(vaccinations);

        mockMvc.perform(get("/api/vaccinations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"1\",\"petId\":\"1\",\"vaccinationName\":\"Rabies\",\"vaccinationDate\":\"2023-08-19\",\"nextDueDate\":\"2024-08-19\",\"status\":\"completed\"},{\"id\":\"2\",\"petId\":\"2\",\"vaccinationName\":\"Distemper\",\"vaccinationDate\":\"2023-07-10\",\"nextDueDate\":\"2024-07-10\",\"status\":\"due\"}]"));
    }



    @Test
    void createChildcareProgram_createsChildcareProgram() throws Exception {
        ChildcareProgram newProgram = new ChildcareProgram(null, "Summer Camp", "Fun activities for kids", "5-7 years", new Date() , new Date(), "Active");
        ChildcareProgram createdProgram = new ChildcareProgram("1", "Summer Camp", "Fun activities for kids", "5-7 years", DateUtils.parseDate("2024-08-01"), DateUtils.parseDate("2024-08-15"), "Active");

        when(childcareProgramService.addChildcareProgram(any(ChildcareProgram.class))).thenReturn(createdProgram);

        mockMvc.perform(post("/api/childcare-programs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Summer Camp\",\"description\":\"Fun activities for kids\",\"ageGroup\":\"5-7 years\",\"status\":\"Active\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Summer Camp"))
                .andExpect(jsonPath("$.description").value("Fun activities for kids"))
                .andExpect(jsonPath("$.ageGroup").value("5-7 years"))
                .andExpect(jsonPath("$.status").value("Active"));
    }

    @Test
    void getAllChildcarePrograms_returnsAllChildcarePrograms() throws Exception {
        List<ChildcareProgram> programs = Arrays.asList(
                new ChildcareProgram("1", "Summer Camp", "Fun activities for kids", "5-7 years", null, null, "Active"),
                new ChildcareProgram("2", "Winter Camp", "Winter fun activities", "8-10 years", null, null, "Inactive")
        );

        when(childcareProgramService.getAllChildcarePrograms()).thenReturn(programs);

        mockMvc.perform(get("/api/childcare-programs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Summer Camp"))
                .andExpect(jsonPath("$[0].description").value("Fun activities for kids"))
                .andExpect(jsonPath("$[0].ageGroup").value("5-7 years"))
                .andExpect(jsonPath("$[0].status").value("Active"))
                .andExpect(jsonPath("$[0].startDate").doesNotExist())
                .andExpect(jsonPath("$[0].endDate").doesNotExist())
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Winter Camp"))
                .andExpect(jsonPath("$[1].description").value("Winter fun activities"))
                .andExpect(jsonPath("$[1].ageGroup").value("8-10 years"))
                .andExpect(jsonPath("$[1].status").value("Inactive"))
                .andExpect(jsonPath("$[1].startDate").doesNotExist())
                .andExpect(jsonPath("$[1].endDate").doesNotExist());
    }
    @Test
    void getChildcareProgramById_returnsChildcareProgram() throws Exception {
        ChildcareProgram program = new ChildcareProgram("1", "Summer Camp", "Fun activities for kids", "5-7 years", null, null, "Active");

        when(childcareProgramService.getChildcareProgramById("1")).thenReturn(Optional.of(program));

        mockMvc.perform(get("/api/childcare-programs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Summer Camp"))
                .andExpect(jsonPath("$.description").value("Fun activities for kids"))
                .andExpect(jsonPath("$.ageGroup").value("5-7 years"))
                .andExpect(jsonPath("$.status").value("Active"))
                .andExpect(jsonPath("$.startDate").doesNotExist())
                .andExpect(jsonPath("$.endDate").doesNotExist());
    }
    @Test
    void updateChildcareProgram_updatesChildcareProgram() throws Exception {
        ChildcareProgram updatedProgram = new ChildcareProgram("1", "Summer Camp Updated", "Updated description", "5-7 years", null, null, "Active");

        when(childcareProgramService.updateChildcareProgram(any(ChildcareProgram.class))).thenReturn(updatedProgram);
        when(childcareProgramService.getChildcareProgramById("1")).thenReturn(Optional.of(updatedProgram));

        mockMvc.perform(put("/api/childcare-programs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Summer Camp Updated\",\"description\":\"Updated description\",\"ageGroup\":\"5-7 years\",\"status\":\"Active\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Summer Camp Updated"))
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.ageGroup").value("5-7 years"))
                .andExpect(jsonPath("$.status").value("Active"))
                .andExpect(jsonPath("$.startDate").doesNotExist())
                .andExpect(jsonPath("$.endDate").doesNotExist());
    }
    @Test
    void deleteChildcareProgram_deletesChildcareProgram() throws Exception {
        when(childcareProgramService.getChildcareProgramById("1")).thenReturn(Optional.of(new ChildcareProgram("1", "Summer Camp", "Fun activities for kids", "5-7 years", null, null, "Active")));

        mockMvc.perform(delete("/api/childcare-programs/1"))
                .andExpect(status().isNoContent());
    }






}
