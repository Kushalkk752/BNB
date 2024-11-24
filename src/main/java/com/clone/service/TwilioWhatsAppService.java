package com.clone.service;

import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioWhatsAppService {

    @Value("${twilio.phone.whatsappnumber}")
    private String whatsappnumber;

    public String sendWhatsAppMessage(String toPhoneNumber, String messageBody) {
        try {
            Message message = Message.creator(
                            new PhoneNumber("whatsapp:" + toPhoneNumber),  // To phone number (in WhatsApp format)
                            new PhoneNumber(whatsappnumber),               // From WhatsApp sender (Twilio Sandbox or your WhatsApp-enabled number)
                            messageBody)                                   // Message body
                    .create();
            return "WhatsApp message sent successfully with SID: " + message.getSid();
        } catch (Exception e) {
            return "Error sending message: " + e.getMessage();
        }
    }
}

