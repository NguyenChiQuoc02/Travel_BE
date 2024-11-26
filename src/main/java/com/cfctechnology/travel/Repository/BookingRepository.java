package com.cfctechnology.travel.Repository;

import com.cfctechnology.travel.Model.Booking;
import com.cfctechnology.travel.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findByBookingDateContaining(LocalDate date, Pageable pageable);

    @Query("SELECT SUM(b.amount) FROM Booking b WHERE b.user.userId = :userId")
    Double getTotalAmountByUserId(@Param("userId") Long userId);


}
