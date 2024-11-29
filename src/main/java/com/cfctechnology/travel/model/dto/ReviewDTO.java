package com.cfctechnology.travel.model.dto;

import com.cfctechnology.travel.model.Destination;
import com.cfctechnology.travel.model.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDTO {

    private User user;
    private Destination destination;
    private Integer rating;
    private String comment;
    private LocalDate reviewDate;

}
