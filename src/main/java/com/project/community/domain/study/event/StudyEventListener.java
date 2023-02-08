package com.project.community.domain.study.event;

import com.project.community.domain.notification.Entity.Notification;
import com.project.community.domain.notification.Enums.NotificationType;
import com.project.community.domain.notification.repository.NotificationRepository;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.user.UserPredicates;
import com.project.community.domain.user.entity.User;
import com.project.community.domain.user.entity.UserGroup;
import com.project.community.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Async
@Transactional
@Component
@RequiredArgsConstructor
public class StudyEventListener {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @EventListener
    public void handleStudyCreatedEvent(StudyCreatedEvent studyCreatedEvent){
        StudyGroup studyGroup = studyCreatedEvent.getStudyGroup();
        String message = "새로운 모임이 등록되었습니다.";
        Iterable<User> users = userRepository.findAll(UserPredicates.findByLocationAndSkills(studyGroup.getLocation(), studyGroup.getSkills()));
        users.forEach(user -> {
            createNotification(studyGroup, user, message, NotificationType.CREATED);
        });
    }

    @EventListener
    public void handleStudyUpdateEvent(StudyUpdateEvent studyUpdateEvent){
        StudyGroup studyGroup = studyUpdateEvent.getStudyGroup();
        List<UserGroup> userGroups = studyGroup.getUserGroups();
        Set<User> users = new HashSet<>();
        for(UserGroup userGroup : userGroups){
            users.add(userGroup.getUser());
        }
        users.forEach(user ->{
            createNotification(studyGroup, user, studyUpdateEvent.getMessage(), NotificationType.UPDATED);
        });
    }

    private void createNotification(StudyGroup studyGroup, User user, String message, NotificationType notificationType) {
        Notification notification = new Notification();
        notification.setTitle(studyGroup.getTitle());
        notification.setChecked(false);
        notification.setCreatedDateTime(LocalDateTime.now());
        notification.setMessage(message);
        notification.setStudyGroup(studyGroup);
        notification.setUser(user);
        notification.setNotificationType(notificationType);
        notificationRepository.save(notification);
    }
}
