package com.clone.repository;

import com.clone.entity.Property;
import com.clone.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;

import java.time.LocalDate;

public interface RoomsRepository extends JpaRepository<Rooms, Long> {
    @Query("SELECT r FROM Rooms r WHERE r.date >= :startDate AND r.date < :endDate AND r.type = :type AND r.property = :property")
    List<Rooms> findByTypeAndProperty(
            @Param("startDate") LocalDate fromDate,
            @Param("endDate") LocalDate toDate,
            @Param("type") String type,
            @Param("property") Property property
    );
}