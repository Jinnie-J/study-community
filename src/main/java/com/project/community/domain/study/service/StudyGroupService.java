package com.project.community.domain.study.service;

import com.project.community.domain.study.dto.request.StudyGroupRequest;
import com.project.community.domain.study.dto.response.StudyGroupResponse;
import com.project.community.domain.user.entity.User;

public interface StudyGroupService {

    StudyGroupResponse createStudyGroup(User user, StudyGroupRequest studyGroupRequest);
}
