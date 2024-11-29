package com.cfctechnology.travel.repository;

import com.cfctechnology.travel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByNameContaining(String name, Pageable pageable);
    Optional<User> findByName(String name);
    Boolean existsByName(String name);
    Boolean existsByEmail(String email);

}
