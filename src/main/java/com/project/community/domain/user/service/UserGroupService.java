package com.project.community.domain.user.service;

import com.project.community.domain.enrollment.entity.Enrollment;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.user.entity.User;
import com.project.community.domain.user.entity.UserGroup;

public interface UserGroupService {
    void newEnrollment(UserGroup userGroup, User user);

    void cancelEnrollment(UserGroup userGroup, User user);

    void acceptEnrollment(StudyGroup studyGroup, UserGroup userGroup, Enrollment enrollment);

    void rejectEnrollment(StudyGroup studyGroup, UserGroup userGroup, Enrollment enrollment);
}
