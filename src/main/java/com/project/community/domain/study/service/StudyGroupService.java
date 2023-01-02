package com.project.community.domain.study.service;

import com.project.community.domain.skill.entity.Skill;
import com.project.community.domain.study.dto.request.StudyGroupRequest;
import com.project.community.domain.study.dto.response.StudyGroupResponse;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.user.entity.User;
import org.json.simple.parser.ParseException;

import java.util.List;
import java.util.Set;

public interface StudyGroupService {

    StudyGroupResponse createStudyGroup(User user, StudyGroupRequest studyGroupRequest) throws ParseException, CloneNotSupportedException;

    List<StudyGroupResponse> getAllStudyGroup();

    StudyGroupResponse getStudyGroup(Long studyGroupId);

    void updateStudyGroup(User user, Long studyGroupId, StudyGroupRequest studyGroupRequest) throws ParseException;

    void closeStudyGroup(User user, Long studyGroupId);

    StudyGroup validateDeleteStudyGroup(User user, Long studyGroupId);

    void addSkill(StudyGroupRequest studyGroupRequest, Skill skill,Long studyGroupId);

    Set<Skill> getSkills(Long studyGroupId);
}
