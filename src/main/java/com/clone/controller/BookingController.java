package com.clone.controller;

import com.clone.component.TwilioConfig;
import com.clone.entity.Bookings;
import com.clone.entity.Property;
import com.clone.entity.Rooms;
import com.clone.repository.BookingsRepository;
import com.clone.repository.PropertyRepository;
import com.clone.repository.RoomsRepository;
import com.clone.service.PDFService;
import com.clone.service.TwilioService;
import com.clone.service.TwilioWhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private PDFService pdfService;
    private PropertyRepository propertyRepository;
    private BookingsRepository bookingsRepository;
    private RoomsRepository roomsRepository;
    private TwilioService twilioService;
    private TwilioWhatsAppService twilioWhatsAppService;


    public BookingController(PDFService pdfService, PropertyRepository propertyRepository, BookingsRepository bookingsRepository, RoomsRepository roomsRepository, TwilioService twilioService, TwilioWhatsAppService twilioWhatsAppService) {
        this.pdfService = pdfService;
        this.propertyRepository = propertyRepository;
        this.bookingsRepository = bookingsRepository;
        this.roomsRepository = roomsRepository;
        this.twilioService = twilioService;
        this.twilioWhatsAppService = twilioWhatsAppService;
    }

    @PostMapping("/create-booking")
    private ResponseEntity<?> createBookings(
            @RequestParam long propertyId,
            @RequestParam String type,
            @RequestBody Bookings bookings
    ) {
        Property property = propertyRepository.findById(propertyId).orElseThrow();
        List<Rooms> rooms = roomsRepository.findByTypeAndProperty(bookings.getFromDate(), bookings.getToDate(), type, property);
        for (Rooms room : rooms) {
            if (room.getCount() == 0) {
                return new ResponseEntity<>("No Rooms available for " + room.getDate(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        bookings.setProperty(property);
        Bookings savedBooking = bookingsRepository.save(bookings);
        if (savedBooking != null) {
            for (Rooms roo : rooms) {
                roo.setCount(roo.getCount() - 1);
                roomsRepository.save(roo);
            }
        }
        pdfService.generateBookingPdf("C:\\Users\\NEW\\Desktop\\Programs Intellij\\Hms_bookings\\confirmation-order " + savedBooking.getId() + ".pdf", property);
        twilioService.sendSMS("+916362841854","+14142400344", "Booking confirmed you bookin id is "+bookings.getId());
        twilioWhatsAppService.sendWhatsAppMessage("+916362841854","Booking confirmed you bookin id is "+bookings.getId());
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
}
