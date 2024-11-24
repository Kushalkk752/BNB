package com.clone.service;

import com.clone.entity.AppUser;
import com.clone.entity.Review;
import com.clone.payload.ReviewDto;
import com.clone.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;
    private ModelMapper modelMapper;

    public ReviewService(ReviewRepository reviewRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
    }

    public ReviewDto createReview(ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);
        Review save = reviewRepository.save(review);
        ReviewDto dto = mapToDto(save);
        return dto;
    }

    private ReviewDto mapToDto(Review save) {
        ReviewDto dto = modelMapper.map(save, ReviewDto.class);
        return dto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = modelMapper.map(reviewDto, Review.class);
        return review;
    }

    public List<Review> getAllReviews(AppUser user) {
        List<Review> reviews = reviewRepository.findByAppUser(user);
        return reviews;
    }

    public void delete(long id) {
        reviewRepository.deleteById(id);
    }

}
