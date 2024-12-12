package com.cfctechnology.travel.repository;

import com.cfctechnology.travel.model.Booking;
import com.cfctechnology.travel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findByBookingDateEquals(LocalDate date, Pageable pageable);

    @Query("SELECT SUM(b.amount) FROM Booking b WHERE b.user.userId = :userId")
    Double getTotalAmountByUserId(@Param("userId") Long userId);

    @Query("SELECT b FROM Booking b WHERE b.user.userId = :userId")
    List<Booking> findBookingsByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.user.userId = :userId")
    Long countBookingsByUserId(@Param("userId") Long userId);

}
