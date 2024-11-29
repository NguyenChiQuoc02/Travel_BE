package com.cfctechnology.travel.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TourDTO {
    private String name;
    private Double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String descriptionTour;


}
