package com.cfctechnology.travel.Repository;

import com.cfctechnology.travel.Model.Review;
import com.cfctechnology.travel.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByReviewDateContaining(LocalDate date, Pageable pageable);

}
