package com.cfctechnology.travel.repository;

import com.cfctechnology.travel.model.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    Page<Tour> findByNameContaining(String name, Pageable pageable);

}
