package com.clone.controller;

import com.clone.entity.City;
import com.clone.entity.Country;
import com.clone.entity.Property;
import com.clone.payload.PropertyDto;
import com.clone.repository.CityRepository;
import com.clone.repository.CountryRepository;
import com.clone.repository.PropertyRepository;
import com.clone.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {
    private PropertyService propertyService;
    private PropertyRepository propertyRepository;
    private CityRepository cityRepository;
    private CountryRepository countryRepository;

    public PropertyController(PropertyService propertyService, PropertyRepository propertyRepository, CityRepository cityRepository, CountryRepository countryRepository) {
        this.propertyService = propertyService;
        this.propertyRepository = propertyRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    @PostMapping("/addProperty")
    public ResponseEntity<?> createProperty(
            @RequestBody PropertyDto dto,
            @RequestParam long cityId,
            @RequestParam long countryId
    ) {
        City city = cityRepository.findById(cityId).get();
        Country country = countryRepository.findById(countryId).get();
        dto.setCity(city);
        dto.setCountry(country);
        Optional<Property> opPropertyName = propertyRepository.findPropertyByName(dto.getPropertyName());
        if (opPropertyName.isPresent()) {
            return new ResponseEntity<>("Property already present", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PropertyDto propertyDto = propertyService.createProperty(dto);
        return new ResponseEntity<>(propertyDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PropertyDto>> getAllProperties(){
        List<PropertyDto> allProperties = propertyService.findAllProperties();
        return new ResponseEntity<>(allProperties,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(
            @RequestParam Long id
    ) {
        propertyService.delete(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(
            @PathVariable Long id,
            @RequestBody Property property
    ) {
        Property prop = propertyService.update(id, property);
        return new ResponseEntity<>(prop, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyDto> getPropertyById(
            @PathVariable Long id
    ) {
        PropertyDto propertyDto = propertyService.getPropertyId(id);
        return new ResponseEntity<>(propertyDto, HttpStatus.OK);
    }

    @GetMapping("/search-hotels")
    public List<Property> searchHotels(
            @RequestParam String name
    ){
        List<Property> properties = propertyService.searchAllHotels(name);
        return new ResponseEntity<>(properties,HttpStatus.OK).getBody();
    }
}
