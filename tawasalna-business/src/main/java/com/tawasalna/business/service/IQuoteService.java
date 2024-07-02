package com.tawasalna.business.service;

import com.tawasalna.business.models.Quote;
import com.tawasalna.business.payload.request.CreateQuoteRequest;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IQuoteService {

    List<Quote> getAll();

    ResponseEntity<Quote> getQuote(String serviceId);

    ResponseEntity<List<Quote>> getQuoteByOwnerIdWaiting(String ownerId);

    ResponseEntity<List<Quote>> getQuoteByClientIdWaiting(String clientId);

    ResponseEntity<List<Quote>> getProposeByClientIdWaiting(String clientId);

    ResponseEntity<List<Quote>> getQuotesByClientAndServiceId(String clientId, String serviceId);

    ResponseEntity<List<Quote>> getQuotesByOwnerIdAndServiceId(String ownerId, String serviceId);

    ResponseEntity<List<Quote>> getQuotesByServiceId(String serviceId);

    ResponseEntity<Quote> rejectProposition(String quoteId);

    ResponseEntity<Quote> rejectQuote(String quoteId);

    ResponseEntity<Quote> acceptQuote(String quoteId);

    Quote createQuote(CreateQuoteRequest request);

    ResponseEntity<Quote> proposeQuote(CreateQuoteRequest request, String needId);

    ResponseEntity<ApiResponse> proposeQuoteMobile(CreateQuoteRequest request, String needId);

    ResponseEntity<List<Quote>> getQuoteByOwnerIdAccepted(String ownerId);

    ResponseEntity<List<Quote>> getQuoteByOwnerIdRejected(String ownerId);

    ResponseEntity<List<Quote>> getQuotesByClientIdAccepted(String ownerId);

    ResponseEntity<List<Quote>> getQuotesByClientIdRejected(String ownerId);

    ResponseEntity<Quote> acceptProposition(String quoteId, String needId);

    ResponseEntity<ApiResponse> updateQuote(CreateQuoteRequest updateDto, String quoteId);
}
