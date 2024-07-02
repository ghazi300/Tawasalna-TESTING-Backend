package com.tawasalna.auth.scheduled;

import com.tawasalna.auth.businesslogic.utility.IAuthUtilsService;
import com.tawasalna.auth.models.UserVerifCode;
import com.tawasalna.auth.repository.UserVerifCodeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class ExpiredCodesCleaner {

    private final UserVerifCodeRepository userVerifCodeRepository;
    private final IAuthUtilsService authUtilsService;


    @Scheduled(fixedRate = 60000)
    public void invoke() {
        log.info("Scanning database for expired verification codes...");

        List<UserVerifCode> userVerifCodeList = userVerifCodeRepository.findAll()
                .stream()
                .filter(found -> !authUtilsService.verifyCodeValidity(found))
                .toList();

        if (userVerifCodeList.isEmpty())
            log.info("No expired verif codes found");

        else {
            userVerifCodeRepository.deleteAll(userVerifCodeList);
            log.info("Deleted {} expired verif codes", userVerifCodeList.size());
        }
    }
}
