package com.project.community.domain.study.event;

import com.project.community.domain.notification.Notification;
import com.project.community.domain.notification.NotificationType;
import com.project.community.domain.notification.repository.NotificationRepository;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.user.UserPredicates;
import com.project.community.domain.user.entity.User;
import com.project.community.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
        Iterable<User> users = userRepository.findAll(UserPredicates.findByLocationAndSkills(studyGroup.getLocation(), studyGroup.getSkills()));
        users.forEach(user -> {
            saveStudyCreatedNotification(studyGroup, user);
        });
    }

    private void saveStudyCreatedNotification(StudyGroup studyGroup, User user) {
        String message = studyGroup.getLocation() +"지역에" + studyGroup.getSkills() +"의 기술을 사용하는 모임이 등록되었습니다.";
        Notification notification = new Notification();
        notification.setTitle(studyGroup.getTitle());
        notification.setChecked(false);
        notification.setCreatedDateTime(LocalDateTime.now());
        notification.setMessage(message);
        notification.setUser(user);
        notification.setNotificationType(NotificationType.CREATED);
        notificationRepository.save(notification);
    }
}
