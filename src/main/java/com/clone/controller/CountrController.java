package com.clone.controller;

import com.clone.entity.AppUser;
import com.clone.entity.City;
import com.clone.entity.Country;
import com.clone.payload.AppUserDto;
import com.clone.payload.CityDto;
import com.clone.payload.CountryDto;
import com.clone.repository.AppUserRepository;
import com.clone.repository.CityRepository;
import com.clone.repository.CountryRepository;
import com.clone.service.CityService;
import com.clone.service.CountryService;
import com.clone.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Country")
public class CountrController {
    private CountryService countryService;
    private CountryRepository countryRepository;

    public CountrController(CountryService countryService, CountryRepository countryRepository) {
        this.countryService = countryService;
        this.countryRepository = countryRepository;
    }

    @PostMapping("/addCountry")
    public ResponseEntity<?> createCountry(
            @RequestBody CountryDto dto
    ){
        Optional<Country> opCountryName = countryRepository.findByCountryByName(dto.getCountryName());
        if(opCountryName.isPresent()){
            return new ResponseEntity<>("Country already present", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CountryDto countryDto = countryService.createCountry(dto);
        return new ResponseEntity<>(countryDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries(){
        List<CountryDto> countryDtos = countryService.findAllCountries();
        return new ResponseEntity<>(countryDtos,HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<String> delete(
            @RequestParam Long id
    ){
        countryService.delete(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(
            @PathVariable Long id,
            @RequestBody Country country
    ){
        Country coun = countryService.update(id,country);
        return new ResponseEntity<>(coun,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getCountryById(
            @PathVariable Long id
    ){
        CountryDto countryDto = countryService.getCountryId(id);
        return new ResponseEntity<>(countryDto,HttpStatus.OK);
    }
}
