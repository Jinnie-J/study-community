package com.project.community.domain.user.service;

import com.project.community.domain.user.entity.User;
import com.project.community.domain.user.entity.UserGroup;

public interface UserGroupService {
    void newEnrollment(UserGroup userGroup, User user);
}
