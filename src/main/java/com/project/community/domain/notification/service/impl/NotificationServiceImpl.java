package com.project.community.domain.notification.service.impl;

import com.project.community.domain.notification.Entity.Notification;
import com.project.community.domain.notification.repository.NotificationRepository;
import com.project.community.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    @Override
    public void markAsRead(List<Notification> notificationList) {
        notificationList.forEach(n -> n.setChecked(true));
        notificationRepository.saveAll(notificationList);
    }
}
