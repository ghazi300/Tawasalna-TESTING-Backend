package com.tawasalna.auth.businesslogic.staffsignuprequest;

import com.tawasalna.auth.businesslogic.utility.IAuthUtilsService;
import com.tawasalna.auth.exceptions.InvalidAssistanceException;
import com.tawasalna.auth.exceptions.InvalidSignupRequestException;
import com.tawasalna.auth.models.StaffSignupRequest;
import com.tawasalna.auth.models.enums.StaffSignupStatus;
import com.tawasalna.auth.payload.request.StaffSignupRequestDTO;
import com.tawasalna.auth.repository.StaffSignupRequestRepo;
import com.tawasalna.auth.repository.UserRepository;
import com.tawasalna.shared.exceptions.EntityNotFoundException;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.mail.IMailingService;
import com.tawasalna.shared.mail.TemplateVariable;
import com.tawasalna.shared.utils.Consts;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.*;

@AllArgsConstructor
@Transactional
@Service
public class StaffSignUpReqServiceImp implements IstaffSignUpReqService {

    final StaffSignupRequestRepo staffSignupRequestRepo;
    final IAuthUtilsService authUtilsService;
    final UserRepository userRepository;
    private final IMailingService mailingService;


    @Override
    public StaffSignupRequest makesignupRequest(StaffSignupRequestDTO request) {
        StaffSignupRequest signupRequest = new StaffSignupRequest(
                request.getId(),
                request.getAgent(),
                request.getBroker(),
                request.getAdmins(),
                new Date(),
                StaffSignupStatus.WAITING,
                request.getDescription()
        );
        return staffSignupRequestRepo.save(signupRequest);
    }

    @Override
    public List<StaffSignupRequest> getSignupRequestsByAdminId(String adminId) {
        return staffSignupRequestRepo.findByAdminsIdAndStatus(adminId, StaffSignupStatus.WAITING);
    }

    @Override
    public List<StaffSignupRequest> getSignupRequestsByAdminIdAndBroker(String adminId) {
        return  staffSignupRequestRepo.findByAdminsIdAndStatusAndBrokerNotNull(adminId, StaffSignupStatus.WAITING);
    }

    @Override
    public List<StaffSignupRequest> getSignupRequestsByAdminIdAndAgent(String adminId) {
        return  staffSignupRequestRepo.findByAdminsIdAndStatusAndAgentNotNull(adminId, StaffSignupStatus.WAITING);
    }

    @Override
    public ResponseEntity<StaffSignupRequest> acceptRequest(String requestId) throws MessagingException {

        StaffSignupRequest request = staffSignupRequestRepo.findById(requestId).
                orElseThrow(() ->  new EntityNotFoundException("StaffSignupRequest", requestId, "Request not found") );

        request.setStatus(StaffSignupStatus.ACCEPTED);

        String password = alphaNumericString(10);

        List<TemplateVariable> variables = new ArrayList<>();
        variables.add(new TemplateVariable("htmlLink", "http://localhost:/authentification/login"));
        variables.add(new TemplateVariable("password", password));

        // Mettez à jour les informations de l'agent (s'il existe) avec le nouveau mot de passe
        if (request.getAgent() != null) {
            request.getAgent().setPassword(authUtilsService.encodePwd(password));
            userRepository.save(request.getAgent());
            mailingService.sendEmail(request.getAgent().getEmail(), "Acceptance register request", "Acceptrequest.html", variables);
        }

        // Mettez à jour les informations du courtier (s'il existe) avec le nouveau mot de passe
        if (request.getBroker() != null) {
            request.getBroker().setPassword(authUtilsService.encodePwd(password));
            userRepository.save(request.getBroker());
            mailingService.sendEmail(request.getBroker().getEmail(), "Acceptance register request", "Acceptrequest.html", variables);
        }

        StaffSignupRequest updatedRequest = staffSignupRequestRepo.save(request);

        return ResponseEntity.ok(updatedRequest);
    }

    @Override
    public ResponseEntity<StaffSignupRequest> reject(StaffSignupRequestDTO requestDTO, String requestId) throws MessagingException {

        StaffSignupRequest request = staffSignupRequestRepo.findById(requestId)
                .map(r -> {
                    r.setStatus(StaffSignupStatus.REJECTED);
                    r.setDescription(requestDTO.getDescription());
                    return staffSignupRequestRepo.save(r);
                })
                .orElseThrow(() -> new InvalidSignupRequestException(requestId, Consts.SIGNUP_REQUEST_NOT_FOUND));

        if (request != null) {
            List<TemplateVariable> variables = new ArrayList<>();
            variables.add(new TemplateVariable("description", request.getDescription())); // Utiliser la description vérifiée

            if (request.getAgent() != null) {
                userRepository.save(request.getAgent());
                mailingService.sendEmail(request.getAgent().getEmail(), "Reject register request", "Reject.html", variables);
            }

            if (request.getBroker() != null) {
                userRepository.save(request.getBroker());
                mailingService.sendEmail(request.getBroker().getEmail(), "Reject register request", "Reject.html", variables);
            }

            return ResponseEntity.ok(request);
        } else {
            throw new InvalidSignupRequestException(request.getId(), Consts.SIGNUP_REQUEST_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<StaffSignupRequest> Archiver(StaffSignupRequestDTO requestDTO) {
        return null;
    }
    public static String alphaNumericString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
}