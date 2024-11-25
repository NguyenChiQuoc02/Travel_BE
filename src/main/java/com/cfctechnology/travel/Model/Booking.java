package com.cfctechnology.travel.Model;

import com.cfctechnology.travel.Model.Enum.EBookingStatus;
import com.cfctechnology.travel.Model.Enum.EPaymentMethod;
import com.cfctechnology.travel.Model.Enum.EPaymentStatus;
import jakarta.persistence.*;
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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    private LocalDate bookingDate;

    @Enumerated(EnumType.STRING)
    private EBookingStatus status;

    private LocalDate paymentDate;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private EPaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private EPaymentStatus paymentStatus;



}
