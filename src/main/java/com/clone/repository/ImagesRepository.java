package com.clone.repository;


import java.util.List;
import com.clone.entity.Images;
import com.clone.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images, Long> {
    List<Images> findByProperty(Property property);
}