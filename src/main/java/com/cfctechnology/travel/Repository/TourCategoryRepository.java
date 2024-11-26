package com.cfctechnology.travel.Repository;

import com.cfctechnology.travel.Model.TourCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourCategoryRepository extends JpaRepository<TourCategory, Long> {
}
