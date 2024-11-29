package com.cfctechnology.travel.model;

import com.cfctechnology.travel.model.enum_model.ERole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Role name cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ERole name;
}
