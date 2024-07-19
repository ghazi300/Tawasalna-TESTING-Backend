package com.tawasalna.auth.controller;

import com.tawasalna.auth.businesslogic.pmsassistance.IAssistanceService;
import com.tawasalna.auth.models.Assistance;
import com.tawasalna.auth.payload.request.AssistanceDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/assistance")
@Tag(name = "Assistance Controller", description = "Controller with CRUD methods")
public class AssistanceController {

    private final IAssistanceService iAssistanceService;

    @PostMapping("/make")
    public ResponseEntity<Assistance> make(@RequestBody AssistanceDTO assistanceDTO) {
        return new ResponseEntity<>(
                iAssistanceService.assignAssistance(assistanceDTO),
                HttpStatus.CREATED
        );
    }
    @GetMapping("/waitingByAgent/{agentId}")
    public ResponseEntity<List<Assistance>> getAssistancesWaitingByAgent(@PathVariable String agentId) {
        List<Assistance> waitingAssistances = iAssistanceService.getAssistancesWaintingByAgent(agentId);
        return new ResponseEntity<>(waitingAssistances, HttpStatus.OK);
    }
    @GetMapping("/waitingByBroker/{brokerId}")
    public ResponseEntity<List<Assistance>> getAssistancesWaitingByBroker(@PathVariable String brokerId) {
        List<Assistance> waitingAssistances = iAssistanceService.getAssistancesWaintingByBroker(brokerId);
        return new ResponseEntity<>(waitingAssistances, HttpStatus.OK);
    }
    @GetMapping("/accepted/{agentId}")
    public ResponseEntity<List<Assistance>> getAssistancesAccepted(@PathVariable String agentId) {
        List<Assistance> waitingAssistances = iAssistanceService.getAssistancesAcceptedByAgent(agentId);
        return new ResponseEntity<>(waitingAssistances, HttpStatus.OK);
    }
    @GetMapping("/allbyAgent/{agentId}")
    public ResponseEntity<List<Assistance>> getAssistancesByAgent(@PathVariable String agentId) {
        List<Assistance> assistances = iAssistanceService.getAssistancesByAgent(agentId);
        return new ResponseEntity<>(assistances, HttpStatus.OK);
    }
    @GetMapping("/allbyBroker/{brokerId}")
    public ResponseEntity<List<Assistance>> getAssistancesByBroker(@PathVariable String brokerId) {
        List<Assistance> assistances = iAssistanceService.getAssistancesByBrokers(brokerId);
        return new ResponseEntity<>(assistances, HttpStatus.OK);
    }

    @GetMapping("/{agentId}/{offset}/{pageSize}")
    public ResponseEntity<Page<Assistance>> getAssistanceswithPagination(@PathVariable String agentId,
                                                                         @PathVariable int offset, @PathVariable int pageSize) {

        Page<Assistance> assistances = iAssistanceService.getAssistancesByAgentwithPagination(agentId,offset, pageSize);
        return new ResponseEntity<>(assistances, HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<Assistance> getAssistances() {
        return iAssistanceService.displayAssistance();
    }

    @PutMapping("/accept")
    public ResponseEntity<Assistance> acceptAssistance(@RequestBody AssistanceDTO assistanceDTO)throws MessagingException {
        return iAssistanceService.AcceptAssistance(assistanceDTO);
    }
    @PutMapping("/archiver/{id}")
    public void archiverAssistance(@PathVariable String id){
         iAssistanceService.archiver(id);
    }

    @PutMapping("/reject")
    public ResponseEntity<Assistance> rejectAssistance(@RequestBody AssistanceDTO assistanceDTO) throws MessagingException{
        return iAssistanceService.RejectAssistance(assistanceDTO);
    }

}
