package com.project.community.domain.study.service;

import com.project.community.domain.study.dto.request.StudyGroupRequest;
import com.project.community.domain.study.dto.response.StudyGroupResponse;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.user.entity.User;

import java.util.List;

public interface StudyGroupService {

    StudyGroupResponse createStudyGroup(User user, StudyGroupRequest studyGroupRequest);

    List<StudyGroupResponse> getAllStudyGroup();

    StudyGroupResponse getStudyGroup(Long studyGroupId);

    void updateStudyGroup(User user, Long studyGroupId, StudyGroupRequest studyGroupRequest);

    void closeStudyGroup(User user, Long studyGroupId);

    StudyGroup validateDeleteStudyGroup(User user, Long studyGroupId);

}
