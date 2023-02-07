package com.project.community.domain.enrollment.event;

import com.project.community.domain.enrollment.entity.Enrollment;
import com.project.community.domain.notification.Notification;
import com.project.community.domain.notification.NotificationType;
import com.project.community.domain.notification.repository.NotificationRepository;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Async
@Transactional
@Component
@RequiredArgsConstructor
public class EnrollmentEventListener {

    private final NotificationRepository notificationRepository;

    @EventListener
    public void handleEnrollmentEvent(EnrollmentEvent enrollmentEvent){
        Enrollment enrollment = enrollmentEvent.getEnrollment();
        User user = enrollment.getUser();
        StudyGroup studyGroup = enrollment.getUserGroup().getStudyGroup();

        Notification notification = new Notification();
        notification.setTitle("Enrollment");
        notification.setChecked(false);
        notification.setCreatedDateTime(LocalDateTime.now());
        notification.setMessage(enrollmentEvent.getMessage());
        notification.setUser(user);
        notification.setStudyGroup(studyGroup);
        notification.setNotificationType(NotificationType.ENROLLMENT);
        notificationRepository.save(notification);
    }
}
