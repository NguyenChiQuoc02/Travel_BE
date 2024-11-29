package com.cfctechnology.travel.repository;

import com.cfctechnology.travel.model.TourCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourCategoryRepository extends JpaRepository<TourCategory, Long> {
}
