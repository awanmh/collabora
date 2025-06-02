//JwtUtils.java

package com.manajemennilai.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Utility untuk pembuatan dan validasi token JWT.
 */
@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    private SecretKey getSigningKey() {
        try {
            byte[] secretBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
            if (jwtSecret == null || jwtSecret.isEmpty() || secretBytes.length < 64) {
                logger.warn("Kunci rahasia JWT terlalu pendek atau kosong ({} byte). Panjang minimum adalah 64 byte untuk HS512. Menggunakan kunci otomatis sebagai fallback. Silakan perbarui jwt.secret di application.properties.",
                        secretBytes.length);
                return Keys.secretKeyFor(SignatureAlgorithm.HS512); // Fallback ke kunci otomatis
            }
            return Keys.hmacShaKeyFor(secretBytes);
        } catch (Exception e) {
            logger.error("Error saat membuat kunci penandatanganan JWT: {}", e.getMessage(), e);
            throw new RuntimeException("Kunci rahasia JWT tidak valid: " + e.getMessage(), e);
        }
    }

    public String generateToken(UserDetails userDetails) {
        logger.info("Membuat JWT untuk pengguna: {}", userDetails.getUsername());
        try {
            return Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                    .compact();
        } catch (Exception e) {
            logger.error("Error saat membuat token JWT untuk pengguna {}: {}", userDetails.getUsername(), e.getMessage(), e);
            throw new RuntimeException("Gagal membuat token JWT: " + e.getMessage(), e);
        }
    }

    public String extractUsername(String token) {
        try {
            logger.debug("Mengekstrak username dari token: {}...", token.substring(0, Math.min(token.length(), 10)));
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            logger.warn("Token JWT kadaluarsa: {}", e.getMessage());
            throw new IllegalArgumentException("Token JWT telah kadaluarsa");
        } catch (SignatureException e) {
            logger.error("Tanda tangan JWT tidak valid: {}", e.getMessage());
            throw new IllegalArgumentException("Tanda tangan JWT tidak valid");
        } catch (MalformedJwtException e) {
            logger.error("Format token JWT tidak valid: {}", e.getMessage());
            throw new IllegalArgumentException("Format token JWT tidak valid");
        } catch (UnsupportedJwtException e) {
            logger.error("Token JWT tidak didukung: {}", e.getMessage());
            throw new IllegalArgumentException("Token JWT tidak didukung");
        } catch (IllegalArgumentException e) {
            logger.error("Argumen ilegal saat memproses JWT: {}", e.getMessage());
            throw new IllegalArgumentException("Argumen ilegal untuk JWT");
        } catch (JwtException e) {
            logger.error("Error JWT umum: {}", e.getMessage());
            throw new IllegalArgumentException("Error JWT umum: " + e.getMessage());
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            boolean isValid = username.equals(userDetails.getUsername()) && !isTokenExpired(token);
            logger.info("Validasi token untuk pengguna {}: {}", username, isValid ? "valid" : "tidak valid");
            return isValid;
        } catch (IllegalArgumentException e) {
            logger.warn("Validasi token gagal karena: {}", e.getMessage());
            return false;
        } catch (JwtException e) {
            logger.warn("Validasi token gagal: {}", e.getMessage());
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
            boolean expired = expiration.before(new Date());
            logger.debug("Pengecekan kadaluarsa token: expired={}", expired);
            return expired;
        } catch (JwtException e) {
            logger.warn("Pengecekan kadaluarsa token gagal: {}", e.getMessage());
            return true; // Asumsikan kadaluarsa jika ada error parsing
        }
    }
}