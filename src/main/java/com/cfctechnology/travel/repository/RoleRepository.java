package com.cfctechnology.travel.repository;

import com.cfctechnology.travel.model.enum_model.ERole;
import com.cfctechnology.travel.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
