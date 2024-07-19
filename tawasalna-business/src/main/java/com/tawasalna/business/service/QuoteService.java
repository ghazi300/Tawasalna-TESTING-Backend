package com.tawasalna.business.service;

import com.tawasalna.business.exceptions.InvalidServiceException;
import com.tawasalna.business.models.*;
import com.tawasalna.business.models.enums.NeedStatus;
import com.tawasalna.business.models.enums.QuotePhase;
import com.tawasalna.business.models.enums.QuoteStatus;
import com.tawasalna.business.payload.request.CreateQuoteRequest;
import com.tawasalna.business.repository.BusinessServiceRepository;
import com.tawasalna.business.repository.NeedRepository;
import com.tawasalna.business.repository.QuoteRepository;
import com.tawasalna.shared.dtos.ApiResponse;
import com.tawasalna.shared.exceptions.InvalidEntityBaseException;
import com.tawasalna.shared.exceptions.InvalidUserException;
import com.tawasalna.shared.mail.IMailingService;
import com.tawasalna.shared.mail.TemplateVariable;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.shared.userapi.service.IUserConsumerService;
import com.tawasalna.shared.utils.Consts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
@Slf4j
@AllArgsConstructor
public class QuoteService implements IQuoteService {

    private final QuoteRepository quoteRepository;
    private final IUserConsumerService userConsumerService;
    private final BusinessServiceRepository businessServiceRepository;
    private final NeedRepository needRepository;
    private final IMailingService mailingService;

    @Override
    public List<Quote> getAll() {
        return quoteRepository.findAll();
    }

    @Override
    public ResponseEntity<Quote> getQuote(String id) {

        return ResponseEntity.ok(
                quoteRepository.findById(id)
                        .orElseThrow(
                                () -> new InvalidEntityBaseException(id, "Quote Not Found", "Quote"

                                )
                        )
        );

    }

    @Override
    public ResponseEntity<List<Quote>> getQuoteByOwnerIdWaiting(String ownerId) {
        final Users user = userConsumerService
                .getUserById(ownerId)
                .orElseThrow(() ->
                        new InvalidUserException(
                                ownerId,
                                Consts.USER_NOT_FOUND
                        )
                );
        List<Quote> quotes = quoteRepository.findQuotesByBusinessIdAndClientConfirmedTrueAndBusinessConfirmedFalse(user);
        List<Quote> filteredQuotes = quotes.stream()
                .filter(quote -> quote.getStatus() == QuoteStatus.WAITING)
                .toList();
        return ResponseEntity.ok(filteredQuotes);
    }


    @Override
    public ResponseEntity<List<Quote>> getQuoteByClientIdWaiting(String clientId) {
        final Users user = userConsumerService
                .getUserById(clientId)
                .orElseThrow(() ->
                        new InvalidUserException(
                                clientId,
                                Consts.USER_NOT_FOUND
                        )
                );
        List<Quote> quotes = quoteRepository.findQuotesByClientIdAndClientConfirmedTrueAndBusinessConfirmedFalse(user);
        List<Quote> filteredQuotes = quotes.stream()
                .filter(quote -> quote.getStatus() == QuoteStatus.WAITING)
                .toList();
        return ResponseEntity.ok(filteredQuotes);
    }

    @Override
    public ResponseEntity<List<Quote>> getProposeByClientIdWaiting(String clientId) {
        final Users user = userConsumerService
                .getUserById(clientId)
                .orElseThrow(() ->
                        new InvalidUserException(
                                clientId,
                                Consts.USER_NOT_FOUND
                        )
                );
        List<Quote> quotes = quoteRepository.findQuotesByClientIdAndClientConfirmedFalseAndBusinessConfirmedTrue(user);
        List<Quote> filteredQuotes = quotes.stream()
                .filter(quote -> quote.getStatus() == QuoteStatus.WAITING)
                .toList();
        return ResponseEntity.ok(filteredQuotes);
    }

    @Override
    public ResponseEntity<List<Quote>> getQuotesByClientAndServiceId(String clientId, String serviceId) {
        return null;
    }

    @Override
    public ResponseEntity<List<Quote>> getQuotesByOwnerIdAndServiceId(String ownerId, String serviceId) {
        return null;
    }

    @Override
    public ResponseEntity<List<Quote>> getQuotesByServiceId(String serviceId) {
        return null;
    }

    @Override
    public Quote createQuote(CreateQuoteRequest request) {
        BusinessService service = businessServiceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));
        Users owner = userConsumerService.getUserById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        Users client = userConsumerService.getUserById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        for (ReservationPeriod period : request.getReservationPeriods()) {
            if (!isServiceAvailable(service, period)) {
                throw new RuntimeException("Service not available on the specified date/time");
            }

            if (!hasConfirmedQuotesOnDate(service.getId(), period)) {
                throw new RuntimeException("Existing confirmed quotes exist on the specified date");
            }
        }

        List<ServiceFeature> chosenFeatures = getChosenFeatures(service, request.getChosenFeaturesIds());
        if (chosenFeatures.isEmpty()) {
            throw new RuntimeException("No features selected for the quote");
        }

        final Quote quote = new Quote();
        quote.setMessage(request.getMessage());
        quote.setPriceTag(request.getPriceTag());
        quote.setBusinessConfirmed(false);
        quote.setClientConfirmed(true);
        quote.setReservationPeriods(request.getReservationPeriods());
        quote.setClientId(client);
        quote.setServiceId(service);
        quote.setBusinessId(owner);
        quote.setAdditionalCost(request.getAdditionalCost());
        quote.setReasonForAdditionalCost(request.getReasonForAdditionalCost());
        quote.setChosenFeatures(chosenFeatures);
        quote.setStatus(QuoteStatus.WAITING);
        quote.setPhase(QuotePhase.NEGOTIATION);

        return quoteRepository.save(quote);
    }


    @Override
    public ResponseEntity<Quote> proposeQuote(CreateQuoteRequest request, String needId) {
        return new ResponseEntity<>(quoteCreation(request, needId), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse> proposeQuoteMobile(CreateQuoteRequest request, String needId) {
        quoteCreation(request, needId);

        return new ResponseEntity<>(ApiResponse.ofSuccess("Quote sent", 201), HttpStatus.CREATED);
    }

    private Quote quoteCreation(CreateQuoteRequest request, String needId) {
        BusinessService service = businessServiceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Users owner = userConsumerService.getUserById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Users client = userConsumerService.getUserById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Need need = needRepository.findById(needId)
                .orElseThrow(() -> new RuntimeException("Need not found"));

        for (ReservationPeriod period : request.getReservationPeriods()) {
            if (!isServiceAvailable(service, period)) {
                throw new RuntimeException("Service not available on the specified date/time");
            }

            if (!hasConfirmedQuotesOnDate(service.getId(), period)) {
                throw new RuntimeException("Existing confirmed quotes exist on the specified date");
            }
        }

        List<ServiceFeature> chosenFeatures = getChosenFeatures(service, request.getChosenFeaturesIds());
        if (chosenFeatures.isEmpty()) {
            throw new RuntimeException("No features selected for the quote");
        }

        Quote quote = new Quote();
        quote.setMessage(request.getMessage());
        quote.setPriceTag(request.getPriceTag());
        quote.setBusinessConfirmed(true);
        quote.setClientConfirmed(false);
        quote.setReservationPeriods(request.getReservationPeriods());
        quote.setClientId(client);
        quote.setServiceId(service);
        quote.setBusinessId(owner);
        quote.setAdditionalCost(request.getAdditionalCost());
        quote.setReasonForAdditionalCost(request.getReasonForAdditionalCost());
        quote.setChosenFeatures(chosenFeatures);
        quote.setStatus(QuoteStatus.WAITING);
        quote.setPhase(QuotePhase.NEGOTIATION);

        Quote saved = quoteRepository.save(quote);
        boolean quoteExistsInNeed = need.getQuotes().stream()
                .anyMatch(q -> q.getChosenFeatures().equals(quote.getChosenFeatures()));
        if (!quoteExistsInNeed) {
            need.getQuotes().add(saved);
            needRepository.save(need);
        }

        return saved;
    }

    @Override
    public ResponseEntity<List<Quote>> getQuoteByOwnerIdAccepted(String ownerId) {
        final Users user = userConsumerService
                .getUserById(ownerId)
                .orElseThrow(() ->
                        new InvalidUserException(
                                ownerId,
                                Consts.USER_NOT_FOUND
                        )
                );
        List<Quote> quotes = quoteRepository.findQuotesByBusinessIdAndClientConfirmedTrueAndBusinessConfirmedTrue(user);
        List<Quote> filteredQuotes = quotes.stream()
                .filter(quote -> quote.getStatus() == QuoteStatus.ACCEPTED)
                .toList();
        return ResponseEntity.ok(filteredQuotes);
    }

    @Override
    public ResponseEntity<List<Quote>> getQuoteByOwnerIdRejected(String ownerId) {
        final Users user = userConsumerService
                .getUserById(ownerId)
                .orElseThrow(() ->
                        new InvalidUserException(
                                ownerId,
                                Consts.USER_NOT_FOUND
                        )
                );
        List<Quote> quotes = quoteRepository.findQuotesByBusinessIdAndClientConfirmedTrueAndBusinessConfirmedFalse(user);
        List<Quote> filteredQuotes = quotes.stream()
                .filter(quote -> quote.getStatus() == QuoteStatus.REJECTED)
                .toList();
        return ResponseEntity.ok(filteredQuotes);
    }

    @Override
    public ResponseEntity<List<Quote>> getQuotesByClientIdAccepted(String clientId) {
        final Users user = userConsumerService
                .getUserById(clientId)
                .orElseThrow(() ->
                        new InvalidUserException(
                                clientId,
                                Consts.USER_NOT_FOUND
                        )
                );
        List<Quote> quotes = quoteRepository.findQuotesByClientIdAndClientConfirmedTrueAndBusinessConfirmedTrue(user);
        List<Quote> filteredQuotes = quotes.stream()
                .filter(quote -> quote.getStatus() == QuoteStatus.ACCEPTED)
                .toList();
        return ResponseEntity.ok(filteredQuotes);
    }

    @Override
    public ResponseEntity<List<Quote>> getQuotesByClientIdRejected(String clientId) {
        final Users user = userConsumerService
                .getUserById(clientId)
                .orElseThrow(() ->
                        new InvalidUserException(
                                clientId,
                                Consts.USER_NOT_FOUND
                        )
                );
        List<Quote> quotes = quoteRepository.findQuotesByClientId(user);
        List<Quote> filteredQuotes = quotes.stream()
                .filter(quote -> quote.getStatus() == QuoteStatus.REJECTED)
                .toList();
        return ResponseEntity.ok(filteredQuotes);
    }

    @Override
    public ResponseEntity<Quote> acceptProposition(String quoteId, String needId) {
        // Find the quote by ID
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new InvalidEntityBaseException(quoteId, "Quote Not Found", "Quote"));

        // Find the need by ID
        Need need = needRepository.findById(needId)
                .orElseThrow(() -> new InvalidEntityBaseException(needId, "Need Not Found", "Need"));

        // Update the chosen quote
        quote.setClientConfirmed(true);
        quote.setStatus(QuoteStatus.ACCEPTED);

        // Set the chosen quote in the need
        need.setChosenquote(quote);
        need.setNeedStatus(NeedStatus.SATISFIED);


        // Retrieve all quotes associated with the same need
        List<Quote> quotesForNeed = need.getQuotes();

        // Update status for all quotes associated with the need
        for (Quote q : quotesForNeed) {
            if (q.getId().equals(quoteId)) {
                q.setClientConfirmed(true);
                q.setStatus(QuoteStatus.ACCEPTED);
                q.setPhase(QuotePhase.PRE_DELIVRY);
            }
            // Update status to REJECTED for other quotes
            q.setBusinessConfirmed(false);
            q.setStatus(QuoteStatus.REJECTED);
            quoteRepository.save(q);
        }
        final Users client = userConsumerService
                .getUserById(need.getClientId().getId())
                .orElseThrow(() ->
                        new InvalidUserException(
                                need.getClientId().getId(),
                                Consts.USER_NOT_FOUND
                        )
                );
        // Save the updated need (including the chosen quote and updated quotes)
        needRepository.save(need);


        List<TemplateVariable> vars = new ArrayList<>();
        vars.add(new TemplateVariable("clientName", need.getChosenquote().getBusinessId().getBusinessProfile().getBusinessName()));
        vars.add(new TemplateVariable("userName", client.getEmail()));
        vars.add(new TemplateVariable("serviceName", need.getChosenquote().getServiceId().getTitle()));
        vars.add(new TemplateVariable("needInfo", need.getInfo()));  // Assuming need has a description field
        vars.add(new TemplateVariable("reservationDates", formatReservationDates(quote)));

        try {
            mailingService.sendEmail(need.getChosenquote().getBusinessId().getEmail(),
                    "Your Service Was Chosen",
                    "QuoteAccepted.html",
                    vars);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(quote);
        }
        // Return ResponseEntity with the accepted quote
        return ResponseEntity.ok(quoteRepository.save(quote));
    }

    private String formatReservationDates(Quote quote) {
        StringBuilder formattedDates = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        // Assuming the stored dates are in UTC. Adjust if your dates are in a different timezone.
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        List<ReservationPeriod> reservationDates = quote.getReservationPeriods();
        for (ReservationPeriod reservationDate : reservationDates) {
            String startDate = dateFormat.format(reservationDate.getStartDate());
            String startTime = timeFormat.format(reservationDate.getStartDate());
            String endTime = timeFormat.format(reservationDate.getEndDate());

            formattedDates.append(startDate).append(" (").append(startTime).append(" to ").append(endTime).append("), ");
        }

        // Remove the trailing comma and space
        if (formattedDates.length() > 0) {
            formattedDates.setLength(formattedDates.length() - 2);
        }

        return formattedDates.toString();
    }

    @Override
    public ResponseEntity<Quote> rejectProposition(String quoteId) {
        final Quote quote = quoteRepository
                .findById(quoteId)
                .orElseThrow(() ->
                        new InvalidEntityBaseException(
                                quoteId,
                                "Quote Not Found", "Quote"
                        )
                );

        quote.setClientConfirmed(false);
        quote.setStatus(QuoteStatus.REJECTED);

        return ResponseEntity.ok(quoteRepository.save(quote));
    }

    private List<ServiceFeature> getChosenFeatures(BusinessService service, List<String> chosenFeatureIds) {
        List<ServiceFeature> chosenFeatures = new ArrayList<>();

        if (chosenFeatureIds != null) {
            for (String featureId : chosenFeatureIds) {
                for (ServiceFeature feature : service.getAdditionalFeatures()) {
                    if (feature.getId().equals(featureId)) {
                        chosenFeatures.add(feature);
                        break;
                    }
                }
            }
        }

        return chosenFeatures;
    }

    private boolean isServiceAvailable(BusinessService service, ReservationPeriod period) {
        // Implement logic to check if the service is available for the given reservation period
        // This will involve checking the period's startDate and endDate against existing reservations
        return true;
    }

    private boolean hasConfirmedQuotesOnDate(String serviceId, ReservationPeriod period) {
        // Implement logic to check if there are confirmed quotes for the given reservation period
        // This will involve checking the period's startDate and endDate against existing confirmed quotes
        return true;
    }


    @Override
    public ResponseEntity<Quote> rejectQuote(String quoteId) {
        Optional<Quote> optionalQuote = quoteRepository.findById(quoteId);

        if (optionalQuote.isPresent()) {
            Quote quote = optionalQuote.get();
            quote.setStatus(QuoteStatus.REJECTED);
            quote.setBusinessConfirmed(false);
            Quote updatedQuote = quoteRepository.save(quote);

            return ResponseEntity.ok(updatedQuote);
        } else {
            throw new InvalidServiceException(quoteId, "Quote Not Found");
        }
    }

    @Override
    public ResponseEntity<Quote> acceptQuote(String quoteId) {
        Optional<Quote> optionalQuote = quoteRepository.findById(quoteId);

        if (optionalQuote.isPresent()) {
            Quote quote = optionalQuote.get();
            quote.setStatus(QuoteStatus.ACCEPTED);
            quote.setPhase(QuotePhase.PRE_DELIVRY);
            quote.setBusinessConfirmed(true);
            Quote updatedQuote = quoteRepository.save(quote);

            return ResponseEntity.ok(updatedQuote);
        } else {
            throw new InvalidServiceException(quoteId, "Quote Not Found");
        }
    }


    @Override
    public ResponseEntity<ApiResponse> updateQuote(CreateQuoteRequest updateDto, String quoteId) {
        final BusinessService service = businessServiceRepository
                .findById(updateDto.getServiceId())
                .orElseThrow(() ->
                        new InvalidServiceException(
                                updateDto.getServiceId(),
                                "Service Not Found"
                        )
                );
        List<ServiceFeature> chosenFeatures = getChosenFeatures(service, updateDto.getChosenFeaturesIds());

        if (chosenFeatures.isEmpty()) {
            throw new InvalidEntityBaseException("quote", quoteId, "No features selected for the quote");
        }

        final Quote updated = quoteRepository
                .findById(quoteId)
                .map(q -> {
                    q.setMessage(updateDto.getMessage());
                    q.setChosenFeatures(chosenFeatures);
                    q.setPriceTag(updateDto.getPriceTag());
                    q.setReservationPeriods(updateDto.getReservationPeriods());

                    return quoteRepository.save(q);
                })
                .orElseThrow(() -> new InvalidEntityBaseException(quoteId, "Quote not found", "Quote"));

        log.info(updated.toString());

        return ResponseEntity.ok(new ApiResponse("Profile updated successfully!", null, 200));
    }
}
