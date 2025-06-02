    // JwtAuthafilter.java

    package com.manajemennilai.config;

    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
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

        private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

        @Autowired
        private JwtUtils jwtUtils;

        @Autowired
        private UserDetailsService userDetailsService;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            String requestPath = request.getServletPath();
            logger.debug("Memproses permintaan untuk path: {}", requestPath);

            if (requestPath.startsWith("/api/auth/")) {
                logger.debug("Melewatkan filter JWT untuk endpoint autentikasi: {}", requestPath);
                filterChain.doFilter(request, response);
                return;
            }

            String authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                try {
                    username = jwtUtils.extractUsername(token);
                } catch (Exception e) {
                    logger.error("Error saat mengekstrak username dari token: {}", e.getMessage());
                }
            } else {
                logger.debug("Header Authorization yang valid tidak ditemukan");
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtils.validateToken(token, userDetails)) { // Perbaiki dengan parameter UserDetails
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.debug("Pengguna terautentikasi: {}", username);
                } else {
                    logger.warn("Token JWT tidak valid untuk pengguna: {}", username);
                }
            }

            filterChain.doFilter(request, response);
        }
    }