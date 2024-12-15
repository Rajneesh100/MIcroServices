package com.skillshare.login_service.login;



import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {


    private static final String SECRET_KEY = "YourSecretKeyForJWTGenerationYourSecretKeyForJWTGeneration"; // Use a 256-bit key
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());


    private final UserService userService;
    public JwtService(UserService userService) {
        this.userService = userService;
    }



    // Generate JWT Token
    public static String generateToken(Long userId, String email, LocalDateTime timestamp) {
        LocalDateTime truncatedTimestamp = timestamp.truncatedTo(ChronoUnit.SECONDS);

        return Jwts.builder()
                .setSubject("User Authentication") // Token purpose
                .claim("id", userId)               // Add user ID to claims
                .claim("email", email)             // Add email to claims
                .claim("timestamp",truncatedTimestamp.toString())
                .setIssuedAt(new Date())           // Issue date
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiry
                .signWith(KEY, SignatureAlgorithm.HS256) // Sign with secret key
                .compact();
    }

    // Validate JWT Token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            Long UserId =getUserIdFromToken(token);
            String UserEmail = getEmailFromToken(token);
            String timestamp = getTimeStampFromToken(token);
            return this.userService.isLastAuthStampEqual(UserId, timestamp);  // compares the version of tokens
        } catch (JwtException ex) {
            // Handle invalid token scenarios (expired, tampered, etc.)
            System.out.println("Invalid JWT Token: " + ex.getMessage());
            return false;
        }
    }

    // Extract Claims from JWT Token
    public static Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract Specific Claim - ID
    public static Long getUserIdFromToken(String token) {
        return getClaims(token).get("id", Long.class);
    }

    // Extract Specific Claim - Email
    public static String getEmailFromToken(String token) {
        return getClaims(token).get("email", String.class);
    }

    public static String getTimeStampFromToken(String token) {return getClaims(token).get("timestamp", String.class);}

}
