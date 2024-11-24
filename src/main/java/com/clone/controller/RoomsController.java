package com.clone.controller;

import com.clone.entity.Property;
import com.clone.payload.RoomsDto;
import com.clone.repository.PropertyRepository;
import com.clone.service.RoomsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomsController {
    private RoomsService roomsService;
    private PropertyRepository propertyRepository;

    public RoomsController(RoomsService roomsService, PropertyRepository propertyRepository) {
        this.roomsService = roomsService;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/addRoom")
    public ResponseEntity<RoomsDto> addRooms(
            @RequestParam long propertyId,
            @RequestBody RoomsDto dto
    ){
        Property property = propertyRepository.findById(propertyId).get();
        dto.setProperty(property);
        RoomsDto roomsDto = roomsService.addRooms(dto);
        return new ResponseEntity(roomsDto, HttpStatus.CREATED);
    }

}
