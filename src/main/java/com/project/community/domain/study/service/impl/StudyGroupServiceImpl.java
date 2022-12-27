package com.project.community.domain.study.service.impl;

import com.project.community.domain.study.dto.request.StudyGroupRequest;
import com.project.community.domain.study.dto.response.StudyGroupResponse;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.study.repository.StudyGroupRepository;
import com.project.community.domain.study.service.StudyGroupService;
import com.project.community.domain.user.UserType;
import com.project.community.domain.user.entity.User;
import com.project.community.domain.user.entity.UserGroup;
import com.project.community.domain.user.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyGroupServiceImpl implements StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final UserGroupRepository userGroupRepository;

    @Override
    public StudyGroupResponse createStudyGroup(User user, StudyGroupRequest studyGroupRequest) {
        studyGroupRequest.setCreatedBy(user.getNickname());
        StudyGroup studyGroup = StudyGroupRequest.toEntity(studyGroupRequest);
        StudyGroup newStudyGroup = studyGroupRepository.save(studyGroup);

        UserGroup userGroup = UserGroup.builder()
                .studyGroup(newStudyGroup)
                .user(user)
                .userType(UserType.LEADER)
                .build();

        userGroupRepository.save(userGroup);

        return StudyGroupResponse.fromEntity(newStudyGroup);
    }

    @Override
    public List<StudyGroupResponse> getAllStudyGroup() {
        return studyGroupRepository.findAll()
                .stream()
                .map(StudyGroupResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public StudyGroupResponse getStudyGroup(Long studyGroupId) {
        StudyGroup studyGroup = studyGroupRepository.findById(studyGroupId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(studyGroupId + "에 해당하는 스터디가 없습니다.");
                });
        return StudyGroupResponse.fromEntity(studyGroup);
    }

    //수정 - 리더 권한 체크하기
    @Override
    public void updateStudyGroup(User user, Long studyGroupId, StudyGroupRequest studyGroupRequest) {

        StudyGroup studyGroup = studyGroupRepository.findById(studyGroupId)
                .orElseThrow(() -> new IllegalArgumentException(studyGroupId + "에 해당하는 스터디가 없습니다."));

        studyGroup.update(studyGroupRequest.getTitle(), studyGroupRequest.getContent(), studyGroupRequest.getStudyType(),
                studyGroupRequest.getLocation(), studyGroupRequest.getDuration(), studyGroupRequest.getNumberOfMembers(),
                studyGroupRequest.getOnline(), studyGroupRequest.getStudyStartDate());
    }

    @Override
    public void closeStudyGroup(User user, Long studyGroupId) {
        StudyGroup studyGroup = validateDeleteStudyGroup(user, studyGroupId);
        studyGroup.close();
    }

    //삭제 - 리더 권한 체크하기
    @Override
    public StudyGroup validateDeleteStudyGroup(User user, Long studyGroupId) {
        StudyGroup studyGroup = studyGroupRepository.findById(studyGroupId)
                .orElseThrow(() -> new IllegalArgumentException(studyGroupId + "에 해당하는 스터디가 없습니다."));

        return studyGroup;

    }
}
