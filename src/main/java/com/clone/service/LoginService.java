package com.clone.service;

import com.clone.entity.AppUser;
import com.clone.payload.LoginDto;
import com.clone.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private AppUserRepository appUserRepository;
    private JWTService jwtService;

    public LoginService(AppUserRepository appUserRepository, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    public String verifyLogin(LoginDto dto) {
        Optional<AppUser> opUsername = appUserRepository.findByUsername(dto.getUsername());
        if(opUsername.isPresent()){
            AppUser appUser = opUsername.get();
            if(BCrypt.checkpw(dto.getPassword(),appUser.getPassword())){
                String token = jwtService.generateToken(dto.getUsername());
                return token;
            }
            else {
                return null;
            }
        }
        return null;
    }
}
