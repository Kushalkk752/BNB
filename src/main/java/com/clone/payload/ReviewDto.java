package com.clone.payload;

import com.clone.entity.AppUser;
import com.clone.entity.Property;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private Integer rating;
    private String description;
    private Property property;
    private AppUser appUser;
}
