package com.tawasalna.shared.sms;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsServiceImpl implements ISmsService {


    @Value("${tawasalna.app.phoneNumber}")
    private String phoneNumber;

    @Value("${tawasalna.app.twilioID}")
    private String twilioID;

    @Value("${tawasalna.app.twilioSecret}")
    private String twilioSecret;

    @Override
    public boolean deliverMessage(String to, String content) {
        try {
            Twilio.init(twilioID, twilioSecret);
            Message.creator(
                            new com.twilio.type.PhoneNumber(to),
                            new com.twilio.type.PhoneNumber(phoneNumber),
                            content
                    )
                    .create();
            return true;
        } catch (Exception e) {
            log.trace("error in twilio", e);
            return false;
        }
    }
}
