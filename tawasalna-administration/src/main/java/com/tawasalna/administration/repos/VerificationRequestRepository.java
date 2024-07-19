package com.tawasalna.administration.repos;

import com.tawasalna.shared.communityapi.model.Community;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.shared.verificationrequestapi.model.VerificationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRequestRepository extends MongoRepository<VerificationRequest, String> {
    Page<VerificationRequest> findAllByCommunityAndIsAcceptedFalseAndIsArchivedFalse(Community community, Pageable pageable);
    Boolean existsByRequesterAndIsAcceptedFalseAndIsArchivedFalse(Users user);
}
