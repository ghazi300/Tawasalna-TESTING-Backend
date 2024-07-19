package com.tawasalna.auth.repository;

import com.tawasalna.auth.models.Assistance;
import com.tawasalna.auth.models.Users;
import com.tawasalna.auth.models.enums.AssistanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssistanceRepository extends MongoRepository<Assistance,String> {
    List<Assistance> findByAgentID_IdOrderByCreatedAtDesc(String agentId);
    List<Assistance> findByBrokerID_IdOrderByCreatedAtDesc(String brokerId);

    Page<Assistance> findByAgentID_IdOrderByCreatedAtDesc(String agentId, Pageable pageable);

}
