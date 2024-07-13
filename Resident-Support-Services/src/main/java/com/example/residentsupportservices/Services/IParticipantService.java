package com.example.residentsupportservices.Services;

import com.example.residentsupportservices.Entity.Participant;

import java.util.List;

public interface IParticipantService {
    List<Participant> getAllParticipants();
    Participant getParticipantById(Long id);
    Participant createParticipant(Participant participant);
    Participant updateParticipant(Long id, Participant participant);
    void deleteParticipant(Long id);
}
