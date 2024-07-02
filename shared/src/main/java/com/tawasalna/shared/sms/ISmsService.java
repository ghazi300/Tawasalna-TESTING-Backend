package com.tawasalna.shared.sms;


public interface ISmsService {

    boolean deliverMessage(String to, String content);
}
