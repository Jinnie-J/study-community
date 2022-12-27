package com.project.community.domain.user.service;

import com.project.community.domain.enrollment.entity.Enrollment;
import com.project.community.domain.user.entity.User;
import com.project.community.domain.user.entity.UserGroup;

public interface UserGroupService {
    void newEnrollment(UserGroup userGroup, User user);

    void cancelEnrollment(UserGroup userGroup, User user);

    void acceptEnrollment(UserGroup userGroup, Enrollment enrollment);

    void rejectEnrollment(UserGroup userGroup, Enrollment enrollment);
}
