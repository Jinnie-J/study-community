package com.project.community.domain.study.service;

import com.project.community.domain.study.dto.request.StudyGroupRequest;
import com.project.community.domain.study.dto.response.StudyGroupResponse;
import com.project.community.domain.user.entity.User;

import java.util.List;

public interface StudyGroupService {

    StudyGroupResponse createStudyGroup(User user, StudyGroupRequest studyGroupRequest);

    List<StudyGroupResponse> getAllStudyGroup();
}
