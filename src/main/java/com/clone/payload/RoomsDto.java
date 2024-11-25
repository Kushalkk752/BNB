package com.clone.payload;

import com.clone.entity.Property;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class RoomsDto {
    private String type;
    private Integer count;
    private LocalDate date;
//    private Double pricePerNight;
    private Property property;
}
