package com.example.residentsupportservices.Repository;

import com.example.residentsupportservices.Entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
