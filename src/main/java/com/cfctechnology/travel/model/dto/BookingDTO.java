package com.cfctechnology.travel.model.dto;

import com.cfctechnology.travel.model.Tour;
import com.cfctechnology.travel.model.User;
import com.cfctechnology.travel.model.enum_model.EBookingStatus;
import com.cfctechnology.travel.model.enum_model.EPaymentMethod;
import com.cfctechnology.travel.model.enum_model.EPaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDTO {
    private User user;

    private Tour tour;

    private LocalDate bookingDate;

    private EBookingStatus status;

    private LocalDate paymentDate;

    private Double amount;

    private EPaymentMethod paymentMethod;

    private EPaymentStatus paymentStatus;
}
