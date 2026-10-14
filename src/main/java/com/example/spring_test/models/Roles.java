package com.example.spring_test.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(name = "roleName", nullable = false)
    private String roleName;

    @Column(name = "roleCode", nullable = false)
    private String roleCode;

    @Column(name = "isActive")
    private boolean isActive = true;
}