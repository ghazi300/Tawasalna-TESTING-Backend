package com.example.residentsupportservices.services;

import com.example.residentsupportservices.entity.Participant;

import java.util.List;

public interface IParticipantService {
    List<Participant> getAllParticipants();
    Participant getParticipantById(String id);
    Participant createParticipant(Participant participant);
    Participant updateParticipant(String id, Participant participant);
    void deleteParticipant(String id);
}
