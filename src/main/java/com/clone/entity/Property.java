package com.clone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "property_name", nullable = false)
    private String propertyName;

    @Column(name = "no_of_guests", nullable = false)
    private String no_of_guests;

    @Column(name = "no_of_rooms", nullable = false)
    private Integer no_of_rooms;  // Total number of rooms in the hotel

    @Column(name = "no_of_bathrooms", nullable = false)
    private String no_of_bathrooms;

    @Column(name = "no_of_beds", nullable = false)
    private String no_of_beds;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

}