    package com.example.residentsupportservices.controllers;

    import com.example.residentsupportservices.Entity.Pet;
    import com.example.residentsupportservices.Entity.PetBoarding;
    import com.example.residentsupportservices.Entity.Vaccination;
    import com.example.residentsupportservices.Services.IPetBoardingService;
    import com.example.residentsupportservices.Services.IPetService;
    import com.example.residentsupportservices.Services.IVaccinationService;
    import com.example.residentsupportservices.entity.Attendance;
    import com.example.residentsupportservices.entity.Event;
    import com.example.residentsupportservices.entity.Feedback;
    import com.example.residentsupportservices.entity.Participant;
    import com.example.residentsupportservices.services.IAttendanceService;
    import com.example.residentsupportservices.services.IEventService;
    import com.example.residentsupportservices.services.IFeedbackService;
    import com.example.residentsupportservices.services.IParticipantService;
    import lombok.AllArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import org.springframework.http.HttpStatus;

    @RestController
    @AllArgsConstructor
    @RequestMapping("/api")
    public class ControlleursApi {

        private IEventService eventService;
        private IParticipantService participantService;
        private IFeedbackService feedbackService;
        private IAttendanceService attendanceService;
        private IPetService petService;
        private IVaccinationService vaccinationService;

        private IPetBoardingService petBoardingService;

        // Endpoints pour l'entité Event

        @GetMapping("/events")
        public List<Event> getAllEvents() {
            return eventService.getAllEvents();
        }

        @GetMapping("/events/{eventId}")
        public Event getEventById(@PathVariable String eventId) {
            return eventService.getEventById(eventId);
        }

        @PostMapping("/events")
        public Event createEvent(@RequestBody Event event) {
            return eventService.createEvent(event);
        }

        @PutMapping("/events/{eventId}")
        public Event updateEvent(@PathVariable String eventId, @RequestBody Event event) {
            return eventService.updateEvent(eventId, event);
        }

        @DeleteMapping("/events/{eventId}")
        public void deleteEvent(@PathVariable String eventId) {
            eventService.deleteEvent(eventId);
        }

    // Endpoints pour l'entité Participant

    @GetMapping("/participants")
    public List<Participant> getAllParticipants() {
        return participantService.getAllParticipants();
    }

    @GetMapping("/participants/{participantId}")
    public Participant getParticipantById(@PathVariable String participantId) {
        return participantService.getParticipantById(participantId);
    }

    @PostMapping("/participants")
    public Participant createParticipant(@RequestBody Participant participant) {
        return participantService.createParticipant(participant);
    }

    @PutMapping("/participants/{participantId}")
    public Participant updateParticipant(@PathVariable String participantId, @RequestBody Participant participant) {
        return participantService.updateParticipant(participantId, participant);
    }

    @DeleteMapping("/participants/{participantId}")
    public void deleteParticipant(@PathVariable String participantId) {
        participantService.deleteParticipant(participantId);
    }

    // Endpoints pour l'entité Feedback

    @GetMapping("/feedbacks")
    public List<Feedback> getAllFeedbacks() {
        return feedbackService.getAllFeedbacks();
    }

    @GetMapping("/feedbacks/{feedbackId}")
    public Feedback getFeedbackById(@PathVariable String feedbackId) {
        return feedbackService.getFeedbackById(feedbackId);
    }

    @PostMapping("/feedbacks")
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        return feedbackService.createFeedback(feedback);
    }

    @PutMapping("/feedbacks/{feedbackId}")
    public Feedback updateFeedback(@PathVariable String feedbackId, @RequestBody Feedback feedback) {
        return feedbackService.updateFeedback(feedbackId, feedback);
    }

    @DeleteMapping("/feedbacks/{feedbackId}")
    public void deleteFeedback(@PathVariable String feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
    }

    // Endpoints pour l'entité Attendance

    @GetMapping("/attendances")
    public List<Attendance> getAllAttendances() {
        return attendanceService.getAllAttendances();
    }

    @GetMapping("/attendances/{attendanceId}")
    public Attendance getAttendanceById(@PathVariable String attendanceId) {
        return attendanceService.getAttendanceById(attendanceId);
    }

    @PostMapping("/attendances")
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        System.out.println("Received attendance: " + attendance);
        if (attendance.getEvent() == null || attendance.getParticipantName() == null) {
            throw new IllegalArgumentException("Attendance or required fields are missing");
        }

        Attendance createdAttendance = attendanceService.createAttendance(attendance);
        return new ResponseEntity<>(createdAttendance, HttpStatus.CREATED); // Assurez-vous d'importer HttpStatus
    }



    @PostMapping("/attendances/mark")
    public ResponseEntity<Attendance> markAttendance(@RequestParam String eventId, @RequestParam String participantName, @RequestParam Boolean attended) {
        Attendance attendance = attendanceService.markAttendance(eventId, participantName, attended);
        return ResponseEntity.ok(attendance);
    }

    @GetMapping("/attendances/event/{eventId}")
    public ResponseEntity<List<Attendance>> getAttendancesForEvent(@PathVariable String eventId) {
        List<Attendance> attendances = attendanceService.getAttendancesForEvent(eventId);
        return ResponseEntity.ok(attendances);
    }



    @PutMapping("/attendances/{attendanceId}")
    public Attendance updateAttendance(@PathVariable String attendanceId, @RequestBody Attendance attendance) {
        return attendanceService.updateAttendance(attendanceId, attendance);
    }

    @DeleteMapping("/attendances/{attendanceId}")
    public void deleteAttendance(@PathVariable String attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
    }

// Endpoints pour l'entité Pet

    @PostMapping("/pets")
    public ResponseEntity<Pet> addPet(@RequestBody Pet pet) {
        return ResponseEntity.ok(petService.addPet(pet));
    }

    @PutMapping("/pets/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable String id, @RequestBody Pet petDetails) {
        return ResponseEntity.ok(petService.updatePet(id, petDetails));
    }

    @DeleteMapping("/pets/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable String id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable String id) {
        return ResponseEntity.ok(petService.getPetById(id));
    }

    @GetMapping("/pets")
    public ResponseEntity<List<Pet>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }
        // Endpoints pour l'entité Vaccination

        @GetMapping("/vaccinations")
        public List<Vaccination> getAllVaccinations() {
            return vaccinationService.getAllVaccinations();
        }

        @GetMapping("/vaccinations/{vaccinationId}")
        public Vaccination getVaccinationById(@PathVariable String vaccinationId) {
            return vaccinationService.getVaccinationById(vaccinationId).orElse(null);
        }

        @PostMapping("/vaccinations")
        public Vaccination createVaccination(@RequestBody Vaccination vaccination) {
            return vaccinationService.addVaccination(vaccination);
        }

        @PutMapping("/vaccinations/{vaccinationId}")
        public Vaccination updateVaccination(@PathVariable String vaccinationId, @RequestBody Vaccination vaccination) {
            return vaccinationService.updateVaccination(vaccinationId, vaccination);
        }

        @DeleteMapping("/vaccinations/{vaccinationId}")
        public void deleteVaccination(@PathVariable String vaccinationId) {
            vaccinationService.deleteVaccination(vaccinationId);
        }

        @GetMapping("/pets/{petId}/vaccinations")
        public List<Vaccination> getVaccinationsByPetId(@PathVariable String petId) {
            return vaccinationService.getVaccinationsByPetId(petId);
        }
        // Endpoints pour l'entité PetBoarding

        @GetMapping("/pet-boardings")
        public List<PetBoarding> getAllPetBoardings() {
            return petBoardingService.getAllPetBoardings();
        }

        @GetMapping("/pet-boardings/{boardingId}")
        public PetBoarding getPetBoardingById(@PathVariable String boardingId) {
            return petBoardingService.getPetBoardingById(boardingId);
        }

        @PostMapping("/pet-boardings")
        public PetBoarding createPetBoarding(@RequestBody PetBoarding petBoarding) {
            return petBoardingService.addPetBoarding(petBoarding);
        }

        @PutMapping("/pet-boardings/{boardingId}")
        public PetBoarding updatePetBoarding(@PathVariable String boardingId, @RequestBody PetBoarding petBoarding) {
            return petBoardingService.updatePetBoarding(boardingId, petBoarding);
        }

        @DeleteMapping("/pet-boardings/{boardingId}")
        public void deletePetBoarding(@PathVariable String boardingId) {
            petBoardingService.deletePetBoarding(boardingId);
        }
    }
