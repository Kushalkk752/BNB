package com.clone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "url", nullable = false, unique = true, length = 2000)
    private String url;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

}