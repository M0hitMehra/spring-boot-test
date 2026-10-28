package com.example.spring_test.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "access_level")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Access_level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(name = "access_level", nullable = false, length = 50)
    private String accessLevel;

    @Column(name = "access_level_code", nullable = false, unique = true, length = 100)
    private String accessLevelCode;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean active = true;

}
