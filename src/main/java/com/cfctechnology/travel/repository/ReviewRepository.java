package com.cfctechnology.travel.repository;

import com.cfctechnology.travel.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByReviewDateContaining(LocalDate date, Pageable pageable);

}