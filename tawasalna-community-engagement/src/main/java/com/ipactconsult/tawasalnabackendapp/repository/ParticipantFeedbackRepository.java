package com.ipactconsult.tawasalnabackendapp.repository;

import com.ipactconsult.tawasalnabackendapp.models.ParticipantFeedback;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ParticipantFeedbackRepository extends MongoRepository<ParticipantFeedback, String> {

    List<ParticipantFeedback> findByEventId(String eventId);
}
