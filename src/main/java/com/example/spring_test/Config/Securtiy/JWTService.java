package com.example.spring_test.Config.Securtiy;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
public class JWTService {
    private final SecretKey secretKey;

    public JWTService(@Value("${jwt.secret}") String jwtSecret) {
        System.out.println("gellllllll" + jwtSecret);
        this.secretKey = jwtSecretKey(jwtSecret);
        System.out.println("mellllllll" + secretKey);

    }

    @Bean
    public SecretKey jwtSecretKey(
            @Value("${jwt.secret}") String secret
    ) {
        try {
            byte[] decodeKey = Base64.getDecoder().decode(secret);
            return new SecretKeySpec(
                    decodeKey,
                    "HmacSHA256"
            );
        } catch (Exception e) {
            System.out.println("belllllllll");
            System.out.println(e);
        }
        return null;

    }

    public String generateToken(CustomUserDetails userDetails) {
        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(secretKey)
                .compact();

    }

    public String extractUsername(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }

    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername());
    }
}
