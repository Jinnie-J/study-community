package com.project.community.domain.notification.repository;

import com.project.community.domain.notification.Notification;
import com.project.community.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    long countByUserAndChecked(User user, boolean checked);
}
