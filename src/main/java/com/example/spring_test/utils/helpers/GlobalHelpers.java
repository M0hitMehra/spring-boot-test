package com.example.spring_test.utils.helpers;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Data
@NoArgsConstructor
@Component
public class GlobalHelpers {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String ALPHANUM = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    public static String generateCode(String name, int suffix_length) {
        String slug = slugify(name);
        String suffix = randomSuffix(suffix_length < 1 || suffix_length > 10 ? 4 : suffix_length);
        return slug + "-" + suffix;
    }

    private static String slugify(String name) {
        if (name == null || name.isBlank()) return "COMPANY";
        String s = name.trim().toUpperCase()
                .replaceAll("[^A-Z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
        // truncate so slug + "-" + suffix stays within 50 chars
        int maxSlug = 40;
        return s.length() > maxSlug ? s.substring(0, maxSlug) : s;
    }

    private static String randomSuffix(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHANUM.charAt(RANDOM.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }

}
