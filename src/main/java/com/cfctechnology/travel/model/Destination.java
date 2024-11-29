package com.cfctechnology.travel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Destination implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long destinationId;

    @NotBlank(message = "Destination name cannot be blank")
    @Size(max = 255, message = "Destination name cannot exceed 255 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotBlank(message = "Location cannot be blank")
    @Size(max = 255, message = "Location cannot exceed 255 characters")
    private String location;

    @NotBlank(message = "Image URL cannot be blank")
    private String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tour> tours;

    @JsonIgnore
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
