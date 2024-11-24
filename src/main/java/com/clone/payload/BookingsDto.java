package com.clone.payload;

import com.clone.entity.Property;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingsDto {
    private String name;
    private String emailId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Property property;
}
