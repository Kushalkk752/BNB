package com.clone.controller;

import com.clone.entity.AppUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

    @PostMapping
    public AppUser addCountry(
            @AuthenticationPrincipal AppUser user
            ){
        return user;
    }
}
