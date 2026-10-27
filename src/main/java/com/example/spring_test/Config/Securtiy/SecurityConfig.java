package com.example.spring_test.Config.Securtiy;

import com.example.spring_test.middlewares.error.SecurityExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            CustomUserDetailsService customUserDetailsService,
            PasswordEncoder passwordEncoder
    ) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(
                customUserDetailsService
        );

        provider.setPasswordEncoder(passwordEncoder);

        return provider;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            DaoAuthenticationProvider provider,
            SecurityExceptionHandler securityExceptionHandler,
            JWTFilter jwtFilter
    ) {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(provider)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(securityExceptionHandler)
                        .accessDeniedHandler(securityExceptionHandler)
                )

//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/login",
                                        "/signup",
                                        "/register",
                                        "/swagger-ui/**",      // Swagger UI pages
                                        "/swagger-ui.html",    // Swagger UI redirect
                                        "/v3/api-docs/**",     // OpenAPI JSON docs
                                        "/webjars/**")
                                .permitAll()
                                .requestMatchers("/admin/**")
                                .hasRole("ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }

}

