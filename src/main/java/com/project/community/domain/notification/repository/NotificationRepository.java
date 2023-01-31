package com.project.community.domain.notification.repository;

import com.project.community.domain.notification.Notification;
import com.project.community.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    long countByUserAndChecked(User user, boolean checked);
    @Transactional
    List<Notification> findByUserAndCheckedOrderByCreatedDateTimeDesc(User user, boolean checked);

    @Transactional
    void deleteByUserAndChecked(User user, boolean checked);
}
