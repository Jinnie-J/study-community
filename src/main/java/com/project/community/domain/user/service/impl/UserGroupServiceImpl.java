package com.project.community.domain.user.service.impl;

import com.project.community.domain.enrollment.entity.Enrollment;
import com.project.community.domain.enrollment.event.EnrollmentEvent;
import com.project.community.domain.enrollment.repository.EnrollmentRepository;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.user.dto.UserAccount;
import com.project.community.domain.user.entity.User;
import com.project.community.domain.user.entity.UserGroup;
import com.project.community.domain.user.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class UserGroupServiceImpl implements UserGroupService {

    private final EnrollmentRepository enrollmentRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void newEnrollment(UserGroup userGroup, User user) {
        if(!enrollmentRepository.existsByUserGroupAndUser(userGroup, user)){
            Enrollment enrollment = Enrollment.builder()
                    .userGroup(userGroup)
                    .enrolledAt(LocalDateTime.now())
                    .user(user)
                    .build();

            userGroup.addEnrollment(enrollment);
            enrollmentRepository.save(enrollment);
            eventPublisher.publishEvent(new EnrollmentEvent(enrollment, "참가 신청이 완료되었습니다.",userGroup));
        }
    }

    @Override
    public void cancelEnrollment(UserGroup userGroup, User user) {
       Enrollment enrollment = enrollmentRepository.findByUserGroupAndUser(userGroup, user);
       userGroup.removeEnrollment(enrollment);
       enrollmentRepository.delete(enrollment);
        eventPublisher.publishEvent(new EnrollmentEvent(enrollment, "참가 신청을 취소하였습니다.", userGroup));
    }

    @Override
    public void acceptEnrollment(StudyGroup studyGroup, UserGroup userGroup, Enrollment enrollment) {
        userGroup.accept(enrollment);
        studyGroup.addMember();
        eventPublisher.publishEvent(new EnrollmentEvent(enrollment, "모임 리더가 참가 신청을 수락하였습니다.", userGroup));
    }

    @Override
    public void rejectEnrollment(StudyGroup studyGroup, UserGroup userGroup, Enrollment enrollment) {
        userGroup.reject(enrollment);
        UserAccount userAccount = new UserAccount(enrollment.getUser());
        if(userGroup.isAccepted(userAccount)) {
            studyGroup.removeMember();
        }
        eventPublisher.publishEvent(new EnrollmentEvent(enrollment,"모임 리더가 참가 신청을 거절하였습니다.",userGroup));
    }
}
