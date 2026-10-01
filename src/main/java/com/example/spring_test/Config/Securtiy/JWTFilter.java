package com.example.spring_test.Config.Securtiy;

import com.example.spring_test.middlewares.error.ApiException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public JWTFilter(JWTService jwtService, CustomUserDetailsService customUserDetailsService) {
        System.out.println("dellllllllllllll");
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;

    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getServletPath();

        // Skip JWT processing for public endpoints
        if (path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui.html")
                || path.startsWith("/webjars")
                || path.startsWith("/login")
                || path.startsWith("/register")
                || path.startsWith("/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("helllllllllllllll");
        String authHeaders = request.getHeader("Authorization");

        if (authHeaders == null || !authHeaders.startsWith("Bearer ")) {
            throw new ApiException("INVALID_CREDENTIALS", "Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeaders.substring(7);

        String username;

        try {
            username = jwtService.extractUsername(token);
        } catch (Exception exception) {
            filterChain.doFilter(request, response);
            return;
        }

        if (username != null &&
                SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

            UserDetails userDetails =
                    customUserDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(token, userDetails)) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);

    }
}
