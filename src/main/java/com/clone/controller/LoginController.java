package com.clone.controller;

import com.clone.payload.LoginDto;
import com.clone.payload.TokenDto;
import com.clone.service.LoginService;
import com.clone.service.OTPService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class LoginController {
    private LoginService loginService;
    private OTPService otpService;

    public LoginController(LoginService loginService, OTPService otpService) {
        this.loginService = loginService;
        this.otpService = otpService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createUser(
            @RequestBody LoginDto dto
    ) {
        String token = loginService.verifyLogin(dto);
        if (token != null) {
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Invalid Username/Password", HttpStatus.FORBIDDEN);
        }
    }

    // Endpoint to generate OTP for mobile number
    @PostMapping("/login-otp")
    public String generateOtp(@RequestParam String mobileNumber) {
        otpService.generateAndSendOTP(mobileNumber);
        return "otp generated successfully";
    }

    // Endpoint to validate OTP for mobile number
    @PostMapping("/validate-otp")
    public boolean validateOtp(@RequestParam String phoneNumber, @RequestParam String otp) {
        return otpService.validateOTP(phoneNumber, otp);
    }
}

