package com.project.community.domain.notification.service;

import com.project.community.domain.notification.Entity.Notification;

import java.util.List;

public interface NotificationService {
    void markAsRead(List<Notification> notificationList);
}
