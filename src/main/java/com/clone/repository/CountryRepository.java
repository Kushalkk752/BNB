package com.clone.repository;

import com.clone.entity.City;
import com.clone.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query("select c from Country c where c.countryName=:countryName")
    Optional<Country> findByCountryByName(String countryName);
}