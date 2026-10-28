package com.example.spring_test.services;

import com.example.spring_test.middlewares.error.ApiException;
import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.middlewares.response.GlobalResponseHandler;
import com.example.spring_test.models.Access_level;
import com.example.spring_test.models.Employee;
import com.example.spring_test.repositories.*;
import com.example.spring_test.utils.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AccessLevelRepository accessLevelRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public <T> ResponseEntity<ApiResponse<T>> create_user(UserDTO.SignupRequest user) {

        Optional<Employee> optionalUser = employeeRepository.findByEmail(user.email());

        if (optionalUser.isPresent()) {
            throw new ApiException(
                    "USER_ALREADY_EXIST",
                    "User already exists for this email",
                    HttpStatus.ALREADY_REPORTED
            );
        }
//        create a jwt token for user

        Employee new_user = new Employee();
        new_user.setEmail(user.email());
        String code = "EMP_" + user.email().split("@")[0];
        new_user.setEmployeeCode(code);
        new_user.setFirstName(user.firstName());
        new_user.setFirstName(user.middleName());
        new_user.setFirstName(user.lastName());
        if (teamRepository.findByCode(user.team_id()).isEmpty()) {
            throw new ApiException(
                    "TEAM_ID_MISSING",
                    "Team id is required",
                    HttpStatus.UNPROCESSABLE_CONTENT
            );
        }

        if (designationRepository.findByName(user.designation_id()).isEmpty()) {
            throw new ApiException(
                    "DESIGNATION_MISSING",
                    "Designation is required",
                    HttpStatus.UNPROCESSABLE_CONTENT
            );
        }

        if (employeeRepository.findByEmployeeCode(user.manager_id()).isEmpty()) {
            throw new ApiException(
                    "MANAGER_MISSING",
                    "Manager code is required",
                    HttpStatus.UNPROCESSABLE_CONTENT
            );
        }
        new_user.setTeam_id(user.team_id());
        new_user.setDesignation_id(user.designation_id());
        new_user.setManager_id(user.manager_id());
        new_user.setRoles(List.of("admin"));

        new_user.setPassword(passwordEncoder.encode(user.password()));

        employeeRepository.save(new_user);
        return GlobalResponseHandler.success("User created successfully", null, HttpStatus.CREATED);
    }

    public <T> ResponseEntity<ApiResponse<T>> create_user(UserDTO.SignupRequestWithRoles user) {

        Optional<Employee> optionalUser = employeeRepository.findByEmail(user.email());

        if (optionalUser.isPresent()) {
            throw new ApiException(
                    "USER_ALREADY_EXIST",
                    "User already exists for this email",
                    HttpStatus.ALREADY_REPORTED
            );
        }

//        create a jwt token for user

        Employee new_user = new Employee();
        new_user.setEmail(user.email());

        new_user.setPassword(user.password());
        new_user.setRoles(
                user.roles()
                        .stream()
                        .map(roles -> {
                            Access_level searchRoles = accessLevelRepository.findByAccessLevelCode(roles);
                            if (searchRoles == null) {
                                Access_level roles1 = new Access_level();
                                roles1.setAccessLevelCode(roles);
                                roles1.setAccessLevelCode(roles);
                                accessLevelRepository.save(roles1);
                            }
                            return roles;

                        }).toList()
        );

        employeeRepository.save(new_user);
        return GlobalResponseHandler.success("User created successfully", null, HttpStatus.CREATED);
    }
}
