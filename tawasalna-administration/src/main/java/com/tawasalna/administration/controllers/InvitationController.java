package com.tawasalna.administration.controllers;

import com.tawasalna.administration.models.Invitation;
import com.tawasalna.administration.models.enums.InvitationStatus;
import com.tawasalna.administration.payload.request.InvitationDTO;
import com.tawasalna.administration.payload.response.InvitationCreatResp;
import com.tawasalna.administration.services.IInvitationService;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.userapi.model.Users;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invitations")
@AllArgsConstructor
public class InvitationController {
    private final IInvitationService invitationService;

    @PostMapping("/send/{senderId}/{receiverId}")
    public ResponseEntity<InvitationCreatResp> sendInvitation(@PathVariable String senderId, @PathVariable String receiverId) {
        return invitationService.sendInvitation(senderId, receiverId);
    }
    @DeleteMapping("/delete/{invitationId}")
    public ResponseEntity<ApiResponse> deleteInvitation(@PathVariable String invitationId) {
        return invitationService.deleteInvitation(invitationId);
    }

    @GetMapping("/sent/{userId}")
    public List<Invitation> getSentInvitations(@PathVariable String userId) {
        return invitationService.getSentInvitations(userId);
    }

    @GetMapping("/brokers/{brokerId}/agents")
    public List<Users> getAgentsAssociatedWithBroker(@PathVariable String brokerId) {
        return invitationService.getAgentsAssociatedWithBroker(brokerId);
    }

    @GetMapping("/brokers/{brokerId}/suggest-agents")
    public List<Users> suggestAgentsForBroker(@PathVariable String brokerId) {
        return invitationService.suggestAgentsForBroker(brokerId);
    }

    @GetMapping("/pending/broker/{brokerId}")
    public ResponseEntity<List<Invitation>> getPendingInvitationsByBroker(@PathVariable String brokerId) {
        List<Invitation> invitations = invitationService.myPendingInvitationsSentByBroker(brokerId);
        return new ResponseEntity<>(invitations, HttpStatus.OK);
    }

    @GetMapping("/pending/agent/{agentId}")
    public ResponseEntity<List<Invitation>> getPendingInvitationsByAgent(@PathVariable String agentId) {
        List<Invitation> invitations = invitationService.myPendingInvitationsSentByAgent(agentId);
        return new ResponseEntity<>(invitations, HttpStatus.OK);
    }

    /****************For the agent------------*/
     @GetMapping("/agents/{agentId}/brokers")
     public List<Users> getBrokersAssociatedWithAgent(@PathVariable String agentId) {
     return invitationService.getBrokersAssociatedWithAgent(agentId);
     }

     @GetMapping("/agents/{agentId}/suggest-brokers")
     public List<Users> suggestBrokersForAgent(@PathVariable String agentId) {
     return invitationService.suggestBrokersForAgent(agentId);
     }

     @GetMapping("/received/{userId}")
     public List<Invitation> getReceivedInvitationsPending(@PathVariable String userId) {
     return invitationService.getReceivedInvitationsPending(userId);
     }

     /************** Accept and reject invitation *************/
     @PutMapping("/accept/{invitationId}")
     public ResponseEntity<ApiResponse> acceptToInvitation(@PathVariable String invitationId){
         return invitationService.acceptInvitation(invitationId);
     }
    @PutMapping("/reject/{invitationId}")
    public ResponseEntity<ApiResponse> rejectToInvitation(@PathVariable String invitationId){
        return invitationService.rejectInvitation(invitationId);
    }
}
