package com.tawasalna.administration.services;

import com.tawasalna.administration.models.Invitation;
import com.tawasalna.administration.models.enums.InvitationStatus;
import com.tawasalna.administration.payload.response.InvitationCreatResp;
import com.tawasalna.administration.repos.InvitationRespository;
import com.tawasalna.administration.repos.UserRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.EntityNotFoundException;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.shared.utils.Consts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class InvitationServiceImp implements IInvitationService {
    private final InvitationRespository invitationRespository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<InvitationCreatResp> sendInvitation(String senderId, String receiverId) {
        final Users receiver = userRepository.findById(receiverId)
                .orElseThrow(() ->
                        new EntityNotFoundException("user",
                                "receiverid" + receiverId,
                                Consts.USER_NOT_FOUND
                        )
                );
        final Users sender = userRepository.findById(senderId)
                .orElseThrow(() ->
                        new EntityNotFoundException("user",
                                "sender id" + senderId,
                                Consts.USER_NOT_FOUND
                        )
                );
        Invitation invitation = new Invitation();
        invitation.setSender(sender);
        invitation.setReceiver(receiver);
        invitation.setStatus(InvitationStatus.PENDING);
        invitationRespository.save(invitation);
        return new ResponseEntity<>(new InvitationCreatResp(invitation.getId()),
                HttpStatusCode.valueOf(201));
    }

    @Override
    public ResponseEntity<ApiResponse> deleteInvitation(String invitationId) {
        Invitation invitation = invitationRespository
                .findById(invitationId)
                .orElseThrow(() -> new EntityNotFoundException(
                                "invitation",
                                invitationId,
                                "invitation not found"
                        )
                );
        invitationRespository.delete(invitation);
        return ResponseEntity.ok(new ApiResponse("Invitation successfully deleted!", null, 200));

    }


    @Override
    public ResponseEntity<ApiResponse> acceptInvitation(String invitationId) {
        Invitation invitation = invitationRespository
                .findById(invitationId)
                .orElseThrow(() -> new EntityNotFoundException(
                                "invitation",
                                invitationId,
                                "invitation not found"
                        )
                );
        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitationRespository.save(invitation);
        return ResponseEntity.ok(new ApiResponse("Request successfully accepted!", null, 200));

    }

    @Override
    public ResponseEntity<ApiResponse> rejectInvitation(String invitationId) {
        Invitation invitation = invitationRespository
                .findById(invitationId)
                .orElseThrow(() -> new EntityNotFoundException(
                                "invitation",
                                invitationId,
                                "invitation not found"
                        )
                );
        invitation.setStatus(InvitationStatus.REJECTED);
        invitationRespository.save(invitation);
        return ResponseEntity.ok(new ApiResponse("Request successfully rejected!", null, 200));

    }

    @Override
    public List<Invitation> getReceivedInvitationsPending(String agentId) {
        final Users agent = userRepository.findById(agentId)
                .orElseThrow(() ->
                        new EntityNotFoundException("user",
                                "user id" + agentId,
                                Consts.USER_NOT_FOUND
                        )
                );
        return invitationRespository.findByReceiverAndStatus(agent, InvitationStatus.PENDING);
    }

    @Override
    public List<Invitation> getSentInvitations(String userId) {
        final Users user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("user",
                                "user id" + userId,
                                Consts.USER_NOT_FOUND
                        )
                );
        return invitationRespository.findBySender(user);
    }


    /************En tant que broker *************/
    @Override
    public List<Users> suggestAgentsForBroker(String brokerId) {
// Récupérer le broker spécifié
        final Users broker = userRepository.findById(brokerId)
                .orElseThrow(() -> new EntityNotFoundException("user", "user id " + brokerId, Consts.USER_NOT_FOUND));

        // Récupérer tous les agents
        List<Users> allAgents = userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_PMS_AGENT")))
                .collect(Collectors.toList());

        // Récupérer les invitations envoyées par le broker
        List<Invitation> sentInvitationsByBroker = invitationRespository.findBySender(broker);
        // Récupérer les invitations reçues par le broker
        List<Invitation> receivedInvitationsByBroker = invitationRespository.findByReceiver(broker);

        // Récupérer les ID des agents ayant envoyé ou reçu une invitation à/de le broker
        Set<String> agentsWithInvitations = new HashSet<>();
        agentsWithInvitations.addAll(sentInvitationsByBroker.stream()
                .map(invitation -> invitation.getReceiver().getId())
                .collect(Collectors.toSet()));
        agentsWithInvitations.addAll(receivedInvitationsByBroker.stream()
                .map(invitation -> invitation.getSender().getId())
                .collect(Collectors.toSet()));

        // Filtrer les agents qui n'ont ni envoyé ni reçu d'invitation de le broker
        List<Users> usersWithoutInvitationToOrFromBroker = allAgents.stream()
                .filter(agent -> !agentsWithInvitations.contains(agent.getId()))
                .collect(Collectors.toList());

        return usersWithoutInvitationToOrFromBroker;
    }

    @Override
    public List<Users> getAgentsAssociatedWithBroker(String brokerId) {
        final Users broker = userRepository.findById(brokerId)
                .orElseThrow(() ->
                        new EntityNotFoundException("user",
                                "user id" + brokerId,
                                Consts.USER_NOT_FOUND
                        )
                );
        List<Invitation> sentInvitations = invitationRespository.findBySenderAndStatus(broker, InvitationStatus.ACCEPTED);
        List<Invitation> receivedInvitations = invitationRespository.findByReceiverAndStatus(broker, InvitationStatus.ACCEPTED);

        // Collecter les agents associés à cet broker
        List<Users> associatedAgents = sentInvitations.stream()
                .map(Invitation::getReceiver)
                .collect(Collectors.toList());

        associatedAgents.addAll(receivedInvitations.stream()
                .map(Invitation::getSender)
                .collect(Collectors.toList()));

        return associatedAgents;
    }
    /********************************************/

    /******************En tant qu'Agent**************/
    @Override
    public List<Users> getBrokersAssociatedWithAgent(String agentId) {
        final Users agent = userRepository.findById(agentId)
                .orElseThrow(() ->
                        new EntityNotFoundException("user",
                                "user id" + agentId,
                                Consts.USER_NOT_FOUND
                        )
                );
        List<Invitation> sentInvitations = invitationRespository.findBySenderAndStatus(agent, InvitationStatus.ACCEPTED);
        List<Invitation> receivedInvitations = invitationRespository.findByReceiverAndStatus(agent, InvitationStatus.ACCEPTED);

        // Collecter les brokers associés à cet agent
        List<Users> associatedBrokers = sentInvitations.stream()
                .map(Invitation::getReceiver)
                .collect(Collectors.toList());

        associatedBrokers.addAll(receivedInvitations.stream()
                .map(Invitation::getSender)
                .collect(Collectors.toList()));

        return associatedBrokers;
    }

    @Override
    public List<Users> suggestBrokersForAgent(String agentId) {
        // Récupérer l'agent spécifié
        final Users agent = userRepository.findById(agentId)
                .orElseThrow(() -> new EntityNotFoundException("user", "user id " + agentId, Consts.USER_NOT_FOUND));

        List<Users> allBrokers = userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_PMS_BROKER")))
                .collect(Collectors.toList());

        List<Invitation> sentInvitationsByAgent = invitationRespository.findBySender(agent);
        List<Invitation> receivedInvitationsByAgent = invitationRespository.findByReceiver(agent);

        Set<String> brokersWithInvitations = new HashSet<>();
        brokersWithInvitations.addAll(sentInvitationsByAgent.stream()
                .map(invitation -> invitation.getReceiver().getId())
                .collect(Collectors.toSet()));
        brokersWithInvitations.addAll(receivedInvitationsByAgent.stream()
                .map(invitation -> invitation.getSender().getId())
                .collect(Collectors.toSet()));

        // Filtrer les brokers qui n'ont ni envoyé ni reçu d'invitation de l'agent
        List<Users> usersWithoutInvitationToOrFromAgent = allBrokers.stream()
                .filter(broker -> !brokersWithInvitations.contains(broker.getId()))
                .collect(Collectors.toList());

        return usersWithoutInvitationToOrFromAgent;
    }

    @Override
    public List<Invitation> myPendingInvitationsSentByBroker(String brokerId) {
        final Users broker = userRepository.findById(brokerId)
                .orElseThrow(() -> new EntityNotFoundException("user", "user id" + brokerId, Consts.USER_NOT_FOUND));

        return invitationRespository.findBySenderAndStatus(broker, InvitationStatus.PENDING);
    }

    @Override
    public List<Invitation> myPendingInvitationsSentByAgent(String agentId) {
        final Users agent = userRepository.findById(agentId)
                .orElseThrow(() -> new EntityNotFoundException("user", "user id" + agentId, Consts.USER_NOT_FOUND));

        return invitationRespository.findBySenderAndStatus(agent, InvitationStatus.PENDING);
    }

}
