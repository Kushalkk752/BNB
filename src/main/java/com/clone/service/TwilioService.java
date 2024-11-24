package com.clone.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    public String sendSMS(String toPhoneNumber, String fromPhoneNumber, String messageBody) {
        try {
            Message message = Message.creator(
                            new PhoneNumber(toPhoneNumber),  // To phone number
                            new PhoneNumber(fromPhoneNumber),  // From Twilio phone number
                            messageBody)                      // Message content
                    .create();

            return "Message sent successfully with SID: " + message.getSid();
        } catch (Exception e) {
            return "Error sending message: " + e.getMessage();
        }
    }
}
