package com.tawasalna.business.controllers;

import com.tawasalna.business.models.Quote;
import com.tawasalna.business.payload.request.CreateQuoteRequest;
import com.tawasalna.business.service.IQuoteService;
import com.tawasalna.shared.dtos.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/quotes")
@CrossOrigin("*")
public class QuoteController {

    private final IQuoteService quoteService;


    @GetMapping("/findAll")
    public ResponseEntity<List<Quote>> getAllQuotes() {
        return new ResponseEntity<>(quoteService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{quoteId}")
    public ResponseEntity<Quote> getQuoteById(
            @PathVariable("quoteId") String serviceId
    ) {
        return quoteService.getQuote(serviceId);
    }

    @GetMapping("/owner/waiting/{ownerId}")
    public ResponseEntity<List<Quote>> getQuoteByOwnerIdWaiting(
            @PathVariable String ownerId) {

        return quoteService.getQuoteByOwnerIdWaiting(ownerId);
    }

    @GetMapping("/owner/accepted/{ownerId}")
    public ResponseEntity<List<Quote>> getQuoteByOwnerIdAccepted(
            @PathVariable String ownerId) {

        return quoteService.getQuoteByOwnerIdAccepted(ownerId);
    }

    @GetMapping("/owner/rejected/{ownerId}")
    public ResponseEntity<List<Quote>> getQuoteByOwnerIdRejected(
            @PathVariable String ownerId) {

        return quoteService.getQuoteByOwnerIdRejected(ownerId);
    }

    @GetMapping("/client/waiting/{clientId}")
    public ResponseEntity<List<Quote>> getQuoteByClientIdWaiting(
            @PathVariable String clientId) {

        return quoteService.getQuoteByClientIdWaiting(clientId);
    }

    @GetMapping("/client/proposed/{clientId}")
    public ResponseEntity<List<Quote>> getProposed(
            @PathVariable String clientId) {

        return quoteService.getProposeByClientIdWaiting(clientId);
    }

    @GetMapping("/client/accepted/{clientId}")
    public ResponseEntity<List<Quote>> getQuoteByClientIdAccepted(
            @PathVariable String clientId) {

        return quoteService.getQuotesByClientIdAccepted(clientId);
    }

    @GetMapping("/client/rejected/{clientId}")
    public ResponseEntity<List<Quote>> getQuoteByClientIdRejected(
            @PathVariable String clientId) {

        return quoteService.getQuotesByClientIdRejected(clientId);
    }

    @GetMapping("/find/{clientId}/{serviceId}")
    public ResponseEntity<List<Quote>> clientAndServiceId(
            @PathVariable String clientId,
            @PathVariable String serviceId) {

        return quoteService.getQuotesByClientAndServiceId(clientId, serviceId);
    }

    @GetMapping("/find/{ownerId}/{serviceId}")
    public ResponseEntity<List<Quote>> ownerIdAndServiceId(
            @PathVariable String ownerId,
            @PathVariable String serviceId) {

        return quoteService.getQuotesByOwnerIdAndServiceId(ownerId, serviceId);
    }

    @GetMapping("/find/{serviceId}")
    public ResponseEntity<List<Quote>> getQuoteByServiceId(
            @PathVariable String serviceId) {

        return quoteService.getQuotesByServiceId(serviceId);
    }

    @PostMapping("/create")
    public Quote createQuote(@RequestBody CreateQuoteRequest request) {
        return quoteService.createQuote(request);
    }

    @PostMapping("/business/create/{needId}")
    public ResponseEntity<Quote> createQuoteBusiness(
            @RequestBody CreateQuoteRequest request,
            @PathVariable String needId) {
        return quoteService.proposeQuote(request, needId);
    }

    @PostMapping("/business/create-mobile/{needId}")
    public ResponseEntity<ApiResponse> createQuoteBusinessMobile(
            @RequestBody CreateQuoteRequest request,
            @PathVariable String needId) {
        return quoteService.proposeQuoteMobile(request, needId);
    }

    @PutMapping("/accept/{id}")
    public ResponseEntity<Quote> acceptQuote(@PathVariable String id) {
        return quoteService.acceptQuote(id);
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Quote> rejectQuote(@PathVariable String id) {
        return quoteService.rejectQuote(id);
    }

    @PutMapping("/proposition/accept/{needId}/{quoteId}")
    public ResponseEntity<Quote> acceptProposition(
            @PathVariable String needId,
            @PathVariable String quoteId) {
        return quoteService.acceptProposition(quoteId, needId);
    }

    @PutMapping("/proposition/reject/{quoteId}")
    public ResponseEntity<Quote> rejectProposition(
            @PathVariable String quoteId) {
        return quoteService.rejectProposition(quoteId);
    }

    @PutMapping("/update/{quoteId}")
    public ResponseEntity<ApiResponse> updateQuoteDetails(
            @RequestBody CreateQuoteRequest quoteDto,
            @PathVariable("quoteId") String quoteId
    ) {
        return quoteService.updateQuote(quoteDto, quoteId);
    }
}
