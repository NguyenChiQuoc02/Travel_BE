package com.cfctechnology.travel.model;

import com.cfctechnology.travel.model.enum_model.EBookingStatus;
import com.cfctechnology.travel.model.enum_model.EPaymentMethod;
import com.cfctechnology.travel.model.enum_model.EPaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @NotNull(message = "Booking date cannot be null")
    private LocalDate bookingDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Booking status cannot be null")
    private EBookingStatus status;

    @PastOrPresent(message = "Payment date must be in the past or present")
    private LocalDate paymentDate;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment method cannot be null")
    private EPaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment status cannot be null")
    private EPaymentStatus paymentStatus;
}
