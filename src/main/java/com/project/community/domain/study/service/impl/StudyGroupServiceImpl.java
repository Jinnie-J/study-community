package com.project.community.domain.study.service.impl;

import com.project.community.domain.study.dto.request.StudyGroupRequest;
import com.project.community.domain.study.dto.response.StudyGroupResponse;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.study.repository.StudyGroupRepository;
import com.project.community.domain.study.service.StudyGroupService;
import com.project.community.domain.user.entity.User;
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

    @Override
    public StudyGroupResponse createStudyGroup(User user, StudyGroupRequest studyGroupRequest) {
        StudyGroup studyGroup = StudyGroupRequest.toEntity(studyGroupRequest);
        StudyGroup newStudyGroup = studyGroupRepository.save(studyGroup);

        return StudyGroupResponse.fromEntity(newStudyGroup);
    }

    @Override
    public List<StudyGroupResponse> getAllStudyGroup() {
        return studyGroupRepository.findAll()
                .stream()
                .map(StudyGroupResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
