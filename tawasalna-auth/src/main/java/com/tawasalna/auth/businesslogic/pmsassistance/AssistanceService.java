package com.tawasalna.auth.businesslogic.pmsassistance;

import com.tawasalna.auth.businesslogic.utility.IAuthUtilsService;
import com.tawasalna.auth.exceptions.InvalidAssistanceException;
import com.tawasalna.auth.models.Assistance;
import com.tawasalna.auth.models.enums.AssistanceStatus;
import com.tawasalna.auth.payload.request.AssistanceDTO;
import com.tawasalna.auth.repository.AssistanceRepository;
import com.tawasalna.auth.repository.UserRepository;
import com.tawasalna.shared.mail.IMailingService;
import com.tawasalna.shared.mail.TemplateVariable;
import com.tawasalna.shared.utils.Consts;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
@Service
public class AssistanceService implements IAssistanceService{

    final AssistanceRepository repository;
    final UserRepository userRepository;
    final IAuthUtilsService authUtilsService;
    private final IMailingService mailingService;

    @Override
    public Assistance assignAssistance(AssistanceDTO instance) {
        Assistance assistance = new Assistance(
                instance.getId(),
                instance.getAgentID(), // GET AUTO
                instance.getBrokerID(), //GET AUTO
                instance.getProspectID(), // GET AUTO
                AssistanceStatus.WAITING, // GET AUTO
                new Date(), //GET AUTO,
                instance.getDesiredDate(),
                instance.getDescription()
                );
        return repository.save(assistance);
    }

    @Override
    public List<Assistance> displayAssistance() {
        return repository.findAll(Sort.by("createdAt").descending());
    }

    @Override
    public List<Assistance> getAssistancesWaintingByAgent(String agentId) {
            List<Assistance> assistances = repository.findByAgentID_IdOrderByCreatedAtDesc(agentId);
            return assistances.stream()
                    .filter(assistance -> assistance.getStatus() == AssistanceStatus.WAITING)
                    .collect(Collectors.toList());
    }

    @Override
    public List<Assistance> getAssistancesWaintingByBroker(String brokerId) {
        List<Assistance> assistances = repository.findByBrokerID_IdOrderByCreatedAtDesc(brokerId);
        return assistances.stream()
                .filter(assistance -> assistance.getStatus() == AssistanceStatus.WAITING)
                .collect(Collectors.toList());
    }

    @Override
    public List<Assistance> getAssistancesAcceptedByAgent(String agentId) {
        List<Assistance> assistances = repository.findByAgentID_IdOrderByCreatedAtDesc(agentId);
        return assistances.stream()
                .filter(assistance -> assistance.getStatus() == AssistanceStatus.ACCEPTED)
                .collect(Collectors.toList());
    }

    @Override
    public List<Assistance> getAssistancesByAgent(String agentId) {
        return  repository.findByAgentID_IdOrderByCreatedAtDesc(agentId);

    }

    @Override
    public List<Assistance> getAssistancesByBrokers(String brokerId) {
        return repository.findByBrokerID_IdOrderByCreatedAtDesc(brokerId);
    }

    @Override
    public Page<Assistance> getAssistancesByAgentwithPagination(String agentId, int offset, int pagesize) {
        return  repository.findByAgentID_IdOrderByCreatedAtDesc(agentId, (Pageable) PageRequest.of(offset, pagesize));
    }
    @Override
    public ResponseEntity<Assistance> AcceptAssistance(AssistanceDTO assistanceDTO) throws MessagingException {
        Optional<Assistance> optionalAssistance = repository.findById(assistanceDTO.getId());
        String password = alphaNumericString(10);

        if (optionalAssistance.isPresent()) {
            Assistance assistance = optionalAssistance.get();
            assistance.setStatus(AssistanceStatus.ACCEPTED);
            assistance.setDesiredDate(assistanceDTO.getDesiredDate());
            assistance.getProspectID().setPassword(authUtilsService.encodePwd(password));
            Assistance updatedAssistance = repository.save(assistance);
            userRepository.save(assistance.getProspectID());
            // Envoyer l'e-mail après la mise à jour du statut
            String htmlLink = "http://localhost:4200/authentification/login";
            final List<TemplateVariable> variables = new ArrayList<>();
            variables.add(new TemplateVariable("htmlLink", htmlLink));
            variables.add(new TemplateVariable("password", password));
            mailingService.sendEmail(assistance.getProspectID().getEmail(), "Acceptance register request", "Acceptrequest.html", variables);

            return ResponseEntity.ok(updatedAssistance);
        } else {
            throw new InvalidAssistanceException(assistanceDTO.getId(), Consts.ASSISTANCE_NOT_FOUND);
        }
    }


    @Override
    public ResponseEntity<Assistance> RejectAssistance(AssistanceDTO assistanceDTO) throws MessagingException{
        Optional<Assistance> optionalAssistance = repository.findById(assistanceDTO.getId());

        if (optionalAssistance.isPresent()) {
            Assistance assistance = optionalAssistance.get();
            assistance.setStatus(AssistanceStatus.REJECTED);
            assistance.setDescription(assistanceDTO.getDescription());
            Assistance updatedAssistance = repository.save(assistance);
            userRepository.save(assistance.getProspectID());
            // Envoyer l'e-mail après la mise à jour du statut
            final List<TemplateVariable> variables = new ArrayList<>();
            variables.add(new TemplateVariable("description", assistanceDTO.getDescription()));
            mailingService.sendEmail(assistance.getProspectID().getEmail(), "Reject register request", "Reject.html", variables);

            return ResponseEntity.ok(updatedAssistance);
        } else {
            throw new InvalidAssistanceException(assistanceDTO.getId(), Consts.ASSISTANCE_NOT_FOUND);
        }
    }

    @Override
    public void archiver(String id) {
        Assistance assistance = repository.findById(id)
                .orElseThrow(() -> new InvalidAssistanceException(id, Consts.ASSISTANCE_NOT_FOUND));

        assistance.setStatus(AssistanceStatus.ARCHIVED);
        repository.save(assistance);
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
