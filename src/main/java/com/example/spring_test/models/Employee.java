package com.example.spring_test.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "employee_code",
            nullable = false,
            unique = true,
            length = 50
    )
    private String employeeCode;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "middle_name", length = 100)
    private String middleName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 30)
    private String phone;

    //    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "team_id", nullable = false)
    @Column(nullable = false)
    private String team_id;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "designation_id")
    @Column(nullable = false)
    private String designation_id;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "manager_id")
    @Column(nullable = false)
    private String manager_id;

    @Column(name = "joining_date", nullable = false)
    @Builder.Default
    private LocalDate joiningDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(
            name = "employment_status",
            nullable = false,
            length = 30
    )
    private EmploymentStatus employmentStatus =
            EmploymentStatus.ACTIVE;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "current_address_id")
    private EmployeeAddress currentAddress;
    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "permanent_address_id")
    private EmployeeAddress permanentAddress;
    private String password;

    //    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "employee_access_level",
//            joinColumns = @JoinColumn(name = "employee_id"),
//            inverseJoinColumns = @JoinColumn(name = "access_level_id")
//    )
    @Column(name = "access_level", nullable = false)
    private List<String> roles = new ArrayList<>();

    public enum EmploymentStatus {
        ACTIVE,
        ON_LEAVE,
        SUSPENDED,
        RESIGNED,
        TERMINATED
    }
}