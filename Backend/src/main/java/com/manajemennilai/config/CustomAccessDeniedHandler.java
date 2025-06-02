package com.manajemennilai.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpServletResponse.SC_FORBIDDEN);
        error.put("error", "Forbidden");
        error.put("message", "Access Denied: " + accessDeniedException.getMessage());
        error.put("path", request.getRequestURI());

        try {
            objectMapper.writeValue(response.getWriter(), error);
        } catch (IOException e) {
            response.getWriter().write("{\"error\": \"Failed to process access denied response\"}");
            throw e; // Re-throw untuk logging atau penanganan lebih lanjut
        }
    }
}