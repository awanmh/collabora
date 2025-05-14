//JwtUtils.java

package com.manajemennilai.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    private SecretKey getSigningKey() {
        try {
            // Gunakan kunci default jika jwtSecret terlalu pendek
            if (jwtSecret.getBytes().length < 64) {
                logger.warn("JWT secret is too short ({} bytes). Using auto-generated key.", jwtSecret.getBytes().length);
                return Keys.secretKeyFor(SignatureAlgorithm.HS512); // 512-bit key
            }
            return Keys.hmacShaKeyFor(jwtSecret.getBytes());
        } catch (Exception e) {
            logger.error("Error creating signing key: {}", e.getMessage(), e);
            throw new RuntimeException("Invalid JWT secret: " + e.getMessage(), e);
        }
    }

    public String generateToken(UserDetails userDetails) {
        try {
            return Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                    .compact();
        } catch (Exception e) {
            logger.error("Error generating JWT token for user {}: {}", userDetails.getUsername(), e.getMessage(), e);
            throw new RuntimeException("Failed to generate JWT token: " + e.getMessage(), e);
        }
    }

    public String extractUsername(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            return null;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("JWT validation error: {}", e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (JwtException e) {
            logger.error("Error checking token expiration: {}", e.getMessage());
            return true;
        }
    }
}