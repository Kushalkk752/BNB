package com.clone.controller;

import com.clone.entity.AppUser;
import com.clone.entity.Images;
import com.clone.entity.Property;
import com.clone.repository.ImagesRepository;
import com.clone.repository.PropertyRepository;
import com.clone.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    private BucketService bucketService;
    private PropertyRepository propertyRepository;
    private ImagesRepository imagesRepository;

    public ImageController(BucketService bucketService, PropertyRepository propertyRepository, ImagesRepository imagesRepository) {
        this.bucketService = bucketService;
        this.propertyRepository = propertyRepository;
        this.imagesRepository = imagesRepository;
    }

    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadPropertyPhotos(@RequestParam MultipartFile file,
                                                  @PathVariable String bucketName,
                                                  @PathVariable long propertyId,
                                                  @AuthenticationPrincipal AppUser user
    ) {
        Property property = propertyRepository.findById(propertyId).get();
        String imageUrl = bucketService.uploadFile(file, bucketName);
        Images image = new Images();
        image.setUrl(imageUrl);
        image.setProperty(property);
        Images savedImage = imagesRepository.save(image);
        return new ResponseEntity<>(savedImage, HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Images>> getAllImages(
            @PathVariable long propertyId
    ){
        Property property = propertyRepository.findById(propertyId).get();
        List<Images> byProperty = imagesRepository.findByProperty(property);
        return new ResponseEntity<>(byProperty,HttpStatus.OK);
    }
}
