package com.tawasalna.auth.businesslogic.pmsassistance;
import com.tawasalna.auth.models.Assistance;
import com.tawasalna.auth.models.enums.AssistanceStatus;
import com.tawasalna.auth.payload.request.AssistanceDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import javax.mail.MessagingException;
import java.util.List;

public interface IAssistanceService {
    Assistance assignAssistance(AssistanceDTO instance);
    List<Assistance> displayAssistance();
    List<Assistance> getAssistancesWaintingByAgent(String agentId);
    List<Assistance> getAssistancesWaintingByBroker(String brokerId);
    List<Assistance> getAssistancesAcceptedByAgent(String agentId);
    List<Assistance> getAssistancesByAgent(String agentId);
    List<Assistance> getAssistancesByBrokers(String brokerId);
    Page<Assistance> getAssistancesByAgentwithPagination(String agentId, int offset, int pagesize);
    public ResponseEntity<Assistance> AcceptAssistance(AssistanceDTO assistanceDTO)throws MessagingException;
    public ResponseEntity<Assistance> RejectAssistance(AssistanceDTO assistanceDTO)throws MessagingException;
    void archiver(String id);

}
