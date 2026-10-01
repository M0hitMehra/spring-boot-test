package com.example.spring_test.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "roleName", nullable = false)
    private String roleName;

    @Column(name = "roleCode", nullable = false)
    private String roleCode;

    @Column(name = "isActive")
    private boolean isActive = true;

}
