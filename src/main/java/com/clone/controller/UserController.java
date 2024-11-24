package com.clone.controller;

import com.clone.entity.AppUser;
import com.clone.payload.AppUserDto;
import com.clone.repository.AppUserRepository;
import com.clone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;
    private AppUserRepository appUserRepository;

    public UserController(UserService userService,
                          AppUserRepository appUserRepository) {
        this.userService = userService;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(
            @RequestBody AppUserDto dto){
        Optional<AppUser> opUsername = appUserRepository.findByUsername(dto.getUsername());
        if(opUsername.isPresent()){
            return new ResponseEntity<>("Username already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<AppUser> opEmail = appUserRepository.findByEmail(dto.getEmail());
        if(opEmail.isPresent()){
            return new ResponseEntity<>("Email already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        dto.setRole("USER_ROLE");
        String encodedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(4));
        dto.setPassword(encodedPassword);
        AppUserDto appUserDto = userService.createUser(dto);
        return new ResponseEntity<>(appUserDto, HttpStatus.CREATED);
    }

    @PostMapping("/signup-property-owner")
    public ResponseEntity<?> createOwner(
            @RequestBody AppUserDto dto){
        Optional<AppUser> opUsername = appUserRepository.findByUsername(dto.getUsername());
        if(opUsername.isPresent()){
            return new ResponseEntity<>("Username already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<AppUser> opEmail = appUserRepository.findByEmail(dto.getEmail());
        if(opEmail.isPresent()){
            return new ResponseEntity<>("Email already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        dto.setRole("OWNER_ROLE");
        String encodedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(4));
        dto.setPassword(encodedPassword);
        AppUserDto appUserDto = userService.createUser(dto);
        return new ResponseEntity<>(appUserDto, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<AppUserDto>> getAllRegisteredUsers(){
        List<AppUserDto> appUserDtos = userService.getAllRegisteredUsers();
        return new ResponseEntity<>(appUserDtos,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(
            @RequestParam Long id
    ){
        userService.delete(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(
            @PathVariable Long id,
            @RequestBody AppUserDto appUserDto
    ){
        AppUser user = userService.updateUser(id,appUserDto);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDto> getUserById(
            @PathVariable Long id
    ){
        AppUserDto appUserDto = userService.getUserById(id);
        return new ResponseEntity<>(appUserDto,HttpStatus.OK);
    }

}
