package com.example.spring_test.services;

import com.example.spring_test.middlewares.error.ApiException;
import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.middlewares.response.GlobalResponseHandler;
import com.example.spring_test.models.Roles;
import com.example.spring_test.models.User;
import com.example.spring_test.repositories.RolesRepository;
import com.example.spring_test.repositories.UserRepository;
import com.example.spring_test.utils.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public <T> ResponseEntity<ApiResponse<T>> create_user(UserDTO.SignupRequest user) {

        Optional<User> optionalUser = userRepository.findByEmail(user.email());

        if (optionalUser.isPresent()) {
            throw new ApiException(
                    "USER_ALREADY_EXIST",
                    "User already exists for this email",
                    HttpStatus.ALREADY_REPORTED
            );
        }
//        create a jwt token for user

        User new_user = new User();
        new_user.setEmail(user.email());
        new_user.setUsername(user.username());
        new_user.setPassword(passwordEncoder.encode(user.password()));

        userRepository.save(new_user);
        return GlobalResponseHandler.success("User created successfully", null, HttpStatus.CREATED);
    }

    public <T> ResponseEntity<ApiResponse<T>> create_user(UserDTO.SignupRequestWithRoles user) {

        Optional<User> optionalUser = userRepository.findByEmail(user.email());

        if (optionalUser.isPresent()) {
            throw new ApiException(
                    "USER_ALREADY_EXIST",
                    "User already exists for this email",
                    HttpStatus.ALREADY_REPORTED
            );
        }

//        create a jwt token for user

        User new_user = new User();
        new_user.setEmail(user.email());
        new_user.setUsername(user.username());
        new_user.setPassword(user.password());
        new_user.setRoles(
                user.roles()
                        .stream()
                        .map(roles -> {
                            Roles searchRoles = rolesRepository.findByRoleCode(roles);
                            if (searchRoles == null) {
                                Roles roles1 = new Roles();
                                roles1.setRoleCode(roles);
                                roles1.setRoleName(roles);
                                rolesRepository.save(roles1);
                                return roles1;
                            }
                            return searchRoles;

                        }).toList()
        );

        userRepository.save(new_user);
        return GlobalResponseHandler.success("User created successfully", null, HttpStatus.CREATED);
    }
}
