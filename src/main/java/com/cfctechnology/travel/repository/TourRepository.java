package com.cfctechnology.travel.repository;

import com.cfctechnology.travel.model.Booking;
import com.cfctechnology.travel.model.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    Page<Tour> findByNameContaining(String name, Pageable pageable);


    @Query("SELECT t FROM Tour t WHERE (:destinationId IS NULL OR t.destination.destinationId = :destinationId)")
    Page<Tour> findToursByDestinationId(@Param("destinationId") Long destinationId, Pageable pageable);

    @Query("SELECT t FROM Tour t " +
            "WHERE (:name IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:minPrice IS NULL OR t.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR t.price <= :maxPrice) " +
            "AND (:startDate IS NULL OR t.startDate >= :startDate)")
    Page<Tour> searchTours(@Param("name") String name,
                           @Param("minPrice") Double minPrice,
                           @Param("maxPrice") Double maxPrice,
                           @Param("startDate") LocalDate startDate,
                           Pageable pageable);
}
