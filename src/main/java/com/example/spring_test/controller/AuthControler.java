package com.example.spring_test.controller;

import com.example.spring_test.Config.Securtiy.CustomUserDetails;
import com.example.spring_test.Config.Securtiy.CustomUserDetailsService;
import com.example.spring_test.Config.Securtiy.JWTService;
import com.example.spring_test.middlewares.response.ApiResponse;
import com.example.spring_test.middlewares.response.GlobalResponseHandler;
import com.example.spring_test.services.UserService;
import com.example.spring_test.utils.DTO.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthControler {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JWTService jwtService;
    @Autowired
    private UserService userService;

    public AuthControler(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService userDetailsService,
            JWTService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public <T> ResponseEntity<ApiResponse<T>> signup(@Valid @RequestBody UserDTO.SignupRequest user) {

        return userService.create_user(user);

    }

    @PostMapping("/admin/signupWithRoles")
    public <T> ResponseEntity<ApiResponse<T>> signup(@Valid @RequestBody UserDTO.SignupRequestWithRoles user) {
        return userService.create_user(user);
    }

    @GetMapping("/auth")
    public <T> ResponseEntity<ApiResponse<T>> testAuth() {
        return GlobalResponseHandler.success("Tested");
    }

    @PostMapping("/login")
    public <T> ResponseEntity<ApiResponse<T>> login(
            @RequestBody UserDTO.LoginRequest request
    ) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        String token =
                jwtService.generateToken(userDetails);

        return GlobalResponseHandler.success("Logged in succesfully", (T) Map.of("token", token), HttpStatus.OK);
    }

}
