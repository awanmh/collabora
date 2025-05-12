// NotificationService.java

package com.manajemennilai.service;

import com.manajemennilai.dto.response.NotificationResponse;
import com.manajemennilai.model.Notification;
import com.manajemennilai.model.User;
import com.manajemennilai.repository.NotificationRepository;
import com.manajemennilai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service untuk manajemen notifikasi.
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<NotificationResponse> getNotifications() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        return notificationRepository.findByUserIdAndIsReadFalse(user.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void createNotification(String message, User user) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUser(user);
        notification.setRead(false);
        notificationRepository.save(notification);
    }

    private NotificationResponse mapToResponse(Notification notification) {
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setMessage(notification.getMessage());
        response.setUserId(notification.getUser().getId());
        response.setRead(notification.isRead());
        return response;
    }
}