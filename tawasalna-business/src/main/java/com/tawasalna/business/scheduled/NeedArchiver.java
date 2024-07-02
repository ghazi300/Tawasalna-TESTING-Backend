package com.tawasalna.business.scheduled;

import com.tawasalna.business.models.Need;
import com.tawasalna.business.models.enums.NeedStatus;
import com.tawasalna.business.repository.NeedRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class NeedArchiver {
    private final NeedRepository needRepository;

    public NeedArchiver(NeedRepository needRepository) {
        this.needRepository = needRepository;
    }

    @Scheduled(fixedRate = 60000)
    // Runs every minute(Needs That Have ReservationDateEnd expired are set to Status Expired
    public void archiveExpiredNeeds() {
        List<Need> needs = needRepository.findNeedByNeedStatusSearchingAndIsActiveFalseOrderByCreatedAtDesc();
        Date currentDate = new Date();
        System.out.println("SEARCHING FOR EXPIRED NEEDS");
        for (Need need : needs) {
            if (currentDate.after(need.getReservationDayEnd())) {
                System.out.println("Need" + need.getId() + " Expired");
                need.setIsActive(false);
                need.setNeedStatus(NeedStatus.EXPIRED);
                needRepository.save(need);
            }
        }

    }
}
