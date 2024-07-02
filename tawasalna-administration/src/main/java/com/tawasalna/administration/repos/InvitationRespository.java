package com.tawasalna.administration.repos;

import com.tawasalna.administration.models.Invitation;
import com.tawasalna.administration.models.enums.InvitationStatus;
import com.tawasalna.shared.userapi.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRespository extends MongoRepository<Invitation, String> {
    List<Invitation> findByReceiver(Users users);
    List<Invitation> findBySender(Users users);
    List<Invitation> findBySenderAndStatus(Users sender, InvitationStatus status);
    List<Invitation> findByReceiverAndStatus(Users receiver, InvitationStatus status);

}
