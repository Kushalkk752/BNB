package com.clone.repository;

import com.clone.entity.City;
import com.clone.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("select p from Property p where p.propertyName=:propertyName")
    Optional<Property> findPropertyByName(String propertyName);

    @Query("select p from Property p JOIN city c JOIN country co where c.cityName=:name or co.countryName=:name")
    List<Property> searchHotels(@Param("name") String name);
}