package com.tawasalna.administration.services;

import com.tawasalna.administration.models.Invitation;
import com.tawasalna.administration.payload.response.InvitationCreatResp;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.userapi.model.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IInvitationService {

    ResponseEntity<InvitationCreatResp> sendInvitation(String senderId, String receiverId);

    ResponseEntity<ApiResponse> deleteInvitation(String invitationId);

    ResponseEntity<ApiResponse> acceptInvitation(String InvitationId);

    ResponseEntity<ApiResponse> rejectInvitation(String InvitationId);

    List<Invitation> getSentInvitations(String userId);

    List<Invitation> getReceivedInvitationsPending(String userId);

    List<Users> getBrokersAssociatedWithAgent(String agentId); //les brokers d'un agent

    List<Users> getAgentsAssociatedWithBroker(String brokerId); // les agents d'un broker

    List<Users> suggestAgentsForBroker(String brokerId);

    List<Users> suggestBrokersForAgent(String agentId);

    List<Invitation> myPendingInvitationsSentByBroker(String brokerId);

    List<Invitation> myPendingInvitationsSentByAgent(String agentId);
}
