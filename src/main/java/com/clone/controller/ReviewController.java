package com.clone.controller;

import com.clone.entity.AppUser;
import com.clone.entity.Property;
import com.clone.entity.Review;
import com.clone.payload.ReviewDto;
import com.clone.repository.PropertyRepository;
import com.clone.repository.ReviewRepository;
import com.clone.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    private ReviewService reviewService;
    private PropertyRepository propertyRepository;
    private ReviewRepository reviewRepository;


    public ReviewController(ReviewService reviewService, PropertyRepository propertyRepository, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.propertyRepository = propertyRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("/addReview")
    public ResponseEntity<?> addReview(
            @RequestBody ReviewDto reviewDto,
            @RequestParam long propertyId,
            @AuthenticationPrincipal AppUser user
            ){
        Property property = propertyRepository.findById(propertyId).get();
        if(reviewRepository.existsByAppUserAndProperty(user, property)){
            return new ResponseEntity<>("user had already given the review", HttpStatus.OK);
        }
        reviewDto.setProperty(property);
        reviewDto.setAppUser(user);
        ReviewDto dto = reviewService.createReview(reviewDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(
            @AuthenticationPrincipal AppUser user
    ){
        List<Review> reviews = reviewService.getAllReviews(user);
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleted(
            @RequestParam long id
    ){
        reviewService.delete(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

}
