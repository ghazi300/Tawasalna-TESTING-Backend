package com.tawasalna.business.repository;


import com.tawasalna.shared.verificationrequestapi.model.VerificationRequest;
import com.tawasalna.shared.userapi.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationRequestRepository extends MongoRepository<VerificationRequest, String> {

    Optional<VerificationRequest> findVerificationRequestByRequesterAndIsArchivedFalseAndIsAcceptedFalse(Users requester);
}
