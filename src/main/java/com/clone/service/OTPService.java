package com.clone.service;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OTPService {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    private final ConcurrentHashMap<String, String> otpStore = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> otpTimeStore = new ConcurrentHashMap<>();
    private static final long OTP_EXPIRY_TIME = 300000; // 5 minutes

    // Generate OTP for mobile number
    public String generateAndSendOTP(String phoneNumber) {
        // Generate a 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(10000));

        // Store OTP and timestamp
        otpStore.put(phoneNumber, otp);
        otpTimeStore.put(phoneNumber, System.currentTimeMillis());

        // Send OTP via SMS using Twilio
        sendOtpSms(phoneNumber, otp);

        return otp;
    }

    // Send OTP via SMS using Twilio
    private void sendOtpSms(String phoneNumber, String otp) {
        String messageBody = "Your OTP code is: " + otp;
        try {
            Message message = Message.creator(
                    new PhoneNumber(phoneNumber), // To number
                    new PhoneNumber(twilioPhoneNumber), // From Twilio number
                    messageBody
            ).create();
            System.out.println("Sent OTP message with SID: " + message.getSid());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending OTP via SMS: " + e.getMessage());
        }
    }

    // Validate OTP for mobile number
    public boolean validateOTP(String phoneNumber, String otp) {
        // Check if OTP exists for the given phone number
        if (!otpStore.containsKey(phoneNumber)) {
            return false;
        }
        String storedOtp = otpStore.get(phoneNumber);
        Long timestamp = otpTimeStore.get(phoneNumber);

        // Check if OTP has expired (5 minutes expiry)
        if (System.currentTimeMillis() - timestamp > OTP_EXPIRY_TIME) {
            otpStore.remove(phoneNumber);
            otpTimeStore.remove(phoneNumber);
            return false;
        }

        // Validate if OTP matches
        return storedOtp.equals(otp);
    }
}


