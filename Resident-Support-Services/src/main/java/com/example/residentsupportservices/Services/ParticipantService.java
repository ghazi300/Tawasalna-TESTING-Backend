package com.example.residentsupportservices.services;

import com.example.residentsupportservices.entity.Participant;
import com.example.residentsupportservices.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService implements IParticipantService {

    private final ParticipantRepository participantRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Override
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    @Override
    public Participant getParticipantById(String id) {
        Optional<Participant> participant = participantRepository.findById(id);
        return participant.orElse(null); // Handle optional if necessary
    }

    @Override
    public Participant createParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    @Override
    public Participant updateParticipant(String id, Participant participant) {
        if (!participantRepository.existsById(id)) {
            // Handle not found scenario or throw exception
            return null;
        }
        participant.setId(id); // Set the ID to ensure update on existing entity
        return participantRepository.save(participant);
    }

    @Override
    public void deleteParticipant(String id) {
        participantRepository.deleteById(id);
    }
}
