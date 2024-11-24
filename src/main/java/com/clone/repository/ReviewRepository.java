package com.clone.repository;

import com.clone.entity.AppUser;
import com.clone.entity.Property;
import com.clone.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //findByUserId
    List<Review> findByAppUser(AppUser user);

    //findByUserandProperty
    boolean existsByAppUserAndProperty(AppUser appUser, Property property);
}