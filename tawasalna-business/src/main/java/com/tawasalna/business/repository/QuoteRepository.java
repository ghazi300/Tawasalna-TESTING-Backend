package com.tawasalna.business.repository;

import com.tawasalna.business.models.Quote;
import com.tawasalna.business.models.enums.QuotePhase;
import com.tawasalna.shared.userapi.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends MongoRepository<Quote, String> {
    List<Quote> findQuotesByBusinessIdAndClientConfirmedTrueAndBusinessConfirmedFalse(Users business);

    List<Quote> findQuotesByBusinessIdAndClientConfirmedTrueAndBusinessConfirmedTrue(Users business);

    List<Quote> findQuotesByPhase(QuotePhase quotePhase);

    List<Quote> findQuotesByClientIdAndClientConfirmedTrueAndBusinessConfirmedTrue(Users business);

    List<Quote> findQuotesByClientIdAndClientConfirmedTrueAndBusinessConfirmedFalse(Users user);

    List<Quote> findQuotesByClientIdAndClientConfirmedFalseAndBusinessConfirmedTrue(Users user);

    List<Quote> findQuotesByClientId(Users user);

    List<Quote> findQuotesByChosenFeatures_Id(String featureId);

}
