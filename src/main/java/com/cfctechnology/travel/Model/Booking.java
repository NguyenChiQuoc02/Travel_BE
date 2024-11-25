package com.cfctechnology.travel.Model;

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
    private Status status;

    private LocalDate paymentDate;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public enum Status {
        CONFIRMED, PENDING
    }

    public enum PaymentMethod {
        CASH, CARD, TRANSFER
    }

    public enum PaymentStatus {
        SUCCESS, FAILED, PENDING
    }
}
