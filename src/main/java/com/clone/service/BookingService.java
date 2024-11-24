package com.clone.service;

import com.clone.entity.Bookings;
import com.clone.payload.BookingsDto;
import com.clone.repository.BookingsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private BookingsRepository bookingsRepository;
    private ModelMapper modelMapper;

    public BookingService(BookingsRepository bookingsRepository, ModelMapper modelMapper) {
        this.bookingsRepository = bookingsRepository;
        this.modelMapper = modelMapper;
    }


    public Bookings createBooking(BookingsDto dto) {
       Bookings bookings = mapToEntity(dto);
        Bookings savedBooking = bookingsRepository.save(bookings);
        return savedBooking;
    }

    private BookingsDto mapToDto(Bookings savedBooking) {
        BookingsDto dto = modelMapper.map(savedBooking, BookingsDto.class);
        return dto;
    }

    private Bookings mapToEntity(BookingsDto dto) {
        Bookings booking = modelMapper.map(dto, Bookings.class);
        return booking;
    }
}
