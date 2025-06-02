//JwtUtils.java

package com.manajemennilai.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
<<<<<<< HEAD
=======
import io.jsonwebtoken.security.SignatureException;
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
<<<<<<< HEAD
import java.util.Date;

=======
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Utility untuk pembuatan dan validasi token JWT.
 */
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
<<<<<<< HEAD
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
=======
    private int jwtExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) {
        logger.info("Generating JWT for user: {}", userDetails.getUsername());
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (long) jwtExpiration * 1000))
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
    }

    public String extractUsername(String token) {
        try {
<<<<<<< HEAD
=======
            logger.debug("Extracting username from token: {}", token.substring(0, Math.min(token.length(), 10)) + "...");
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
<<<<<<< HEAD
        } catch (JwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            return null;
=======
        } catch (ExpiredJwtException e) {
            logger.warn("Token expired: {}", e.getMessage());
            throw new IllegalArgumentException("JWT token has expired");
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid JWT signature");
        } catch (JwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid JWT token");
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
<<<<<<< HEAD
            return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("JWT validation error: {}", e.getMessage());
=======
            boolean isValid = username.equals(userDetails.getUsername()) && !isTokenExpired(token);
            logger.info("Token validation for user {}: {}", username, isValid ? "valid" : "invalid");
            return isValid;
        } catch (JwtException e) {
            logger.warn("Token validation failed: {}", e.getMessage());
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
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
<<<<<<< HEAD
            return expiration.before(new Date());
        } catch (JwtException e) {
            logger.error("Error checking token expiration: {}", e.getMessage());
=======
            boolean expired = expiration.before(new Date());
            logger.debug("Token expiration check: expired={}", expired);
            return expired;
        } catch (JwtException e) {
            logger.warn("Token expiration check failed: {}", e.getMessage());
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
            return true;
        }
    }
}