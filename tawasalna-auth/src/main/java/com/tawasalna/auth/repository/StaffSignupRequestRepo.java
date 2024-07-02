package com.tawasalna.auth.repository;

import com.tawasalna.auth.models.StaffSignupRequest;
import com.tawasalna.auth.models.enums.StaffSignupStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffSignupRequestRepo extends MongoRepository<StaffSignupRequest, String> {
    List<StaffSignupRequest> findByAdminsIdAndStatus(String adminId, StaffSignupStatus status);
    List<StaffSignupRequest> findByAdminsIdAndStatusAndBrokerNotNull(String adminId, StaffSignupStatus status);
    List<StaffSignupRequest> findByAdminsIdAndStatusAndAgentNotNull(String adminId, StaffSignupStatus status);
List<StaffSignupRequest> findByStatus(StaffSignupStatus status);
}
