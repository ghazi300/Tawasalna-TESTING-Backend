package com.tawasalna.business.scheduled;

import com.tawasalna.business.models.Quote;
import com.tawasalna.business.models.ReservationPeriod;
import com.tawasalna.business.models.enums.QuotePhase;
import com.tawasalna.business.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Component
public class QuoteScheduler {

    private  final QuoteRepository quoteRepository;


    public QuoteScheduler(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Scheduled(fixedRate = 60000) // Runs every minute (adjusting PRE_DELIVRY Status To DELIVRY)
    public void updateQuotePhases() {
        List<Quote> quotes = quoteRepository.findQuotesByPhase(QuotePhase.PRE_DELIVRY);
        Date currentDate = new Date();
        System.out.println("Checking Quotes");

        if (quotes != null) {
            for (Quote quote : quotes) {
                ReservationPeriod firstPeriod = getFirstReservationPeriod(quote.getReservationPeriods());
                if (firstPeriod != null && currentDate.after(firstPeriod.getStartDate())) {
                    quote.setPhase(QuotePhase.DELIVRY);
                    quoteRepository.save(quote);
                }
            }
        }
    }

    private ReservationPeriod getFirstReservationPeriod(List<ReservationPeriod> periods) {
        if (periods == null || periods.isEmpty()) {
            return null;
        }
        return periods.stream().min((p1, p2) -> p1.getStartDate().compareTo(p2.getStartDate())).orElse(null);
    }
}

