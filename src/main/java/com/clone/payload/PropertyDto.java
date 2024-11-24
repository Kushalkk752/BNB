package com.clone.payload;

import com.clone.entity.City;
import com.clone.entity.Country;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyDto {
    private String propertyName;
    private String no_of_guests;
    private Integer totalRooms;  // Total number of rooms in the hotel
    private String no_of_bathrooms;
    private String no_of_beds;
    private Country country;
    private City city;
}
