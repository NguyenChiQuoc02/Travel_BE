package com.cfctechnology.travel.Repository;

import com.cfctechnology.travel.Model.Destination;
import com.cfctechnology.travel.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    Page<Destination> findByNameContaining(String name, Pageable pageable);

}
