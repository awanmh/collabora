// JwtAuthafilter.java

package com.manajemennilai.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
<<<<<<< HEAD
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
=======
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.manajemennilai.security.JwtUtils;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

<<<<<<< HEAD
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

=======
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
<<<<<<< HEAD
        String requestPath = request.getServletPath();
        logger.debug("Processing request for path: {}", requestPath);

        // Skip filter for /api/auth/** endpoints
        if (requestPath.startsWith("/api/auth/")) {
            logger.debug("Skipping JWT filter for auth endpoint: {}", requestPath);
=======
        // Skip filter for /api/auth/** endpoints
        String requestPath = request.getServletPath();
        if (requestPath.startsWith("/api/auth/")) {
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
            filterChain.doFilter(request, response);
            return;
        }

        // Process JWT token for other endpoints
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
<<<<<<< HEAD
            try {
                username = jwtUtils.extractUsername(token);
            } catch (Exception e) {
                logger.error("Error extracting username from token: {}", e.getMessage());
            }
        } else {
            logger.debug("No valid Authorization header found");
=======
            username = jwtUtils.extractUsername(token);
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtils.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
<<<<<<< HEAD
                logger.debug("Authenticated user: {}", username);
            } else {
                logger.warn("Invalid JWT token for user: {}", username);
=======
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
            }
        }

        filterChain.doFilter(request, response);
    }
}