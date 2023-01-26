package com.project.community.domain.study.service.impl;

import com.project.community.domain.enrollment.entity.Enrollment;
import com.project.community.domain.enrollment.repository.EnrollmentRepository;
import com.project.community.domain.skill.entity.Skill;
import com.project.community.domain.skill.repository.SkillRepository;
import com.project.community.domain.study.dto.StudyGroupRequest;
import com.project.community.domain.study.dto.StudyGroupResponse;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.study.repository.StudyGroupRepository;
import com.project.community.domain.study.service.StudyGroupService;
import com.project.community.domain.user.enums.UserType;
import com.project.community.domain.user.entity.User;
import com.project.community.domain.user.entity.UserGroup;
import com.project.community.domain.user.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StudyGroupServiceImpl implements StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final UserGroupRepository userGroupRepository;
    private final SkillRepository skillRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public StudyGroupResponse createStudyGroup(User user, StudyGroupRequest studyGroupRequest) throws ParseException, CloneNotSupportedException {

        // TODO: 기술 스택 추가 하는 로직 개선 하기 (체크박스로 만들어서 parse 과정 없이 insert)
        StudyGroupRequest request = (StudyGroupRequest) studyGroupRequest.clone();
        Set<Skill> skills = new HashSet<>();
        studyGroupRequest.setSkills(skills);

        studyGroupRequest.setCreatedBy(user.getNickname());
        studyGroupRequest.setRemainingSeats((long) Integer.parseInt(studyGroupRequest.getNumberOfMembers()));
        studyGroupRequest.setCreateDate(LocalDateTime.now());
        StudyGroup studyGroup = StudyGroupRequest.toEntity(studyGroupRequest);
        StudyGroup newStudyGroup = studyGroupRepository.save(studyGroup);

        parseSkillJson(request, newStudyGroup.getId());

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
    public void updateStudyGroup(User user, Long studyGroupId, StudyGroupRequest studyGroupRequest) throws ParseException {

        StudyGroup studyGroup = studyGroupRepository.findById(studyGroupId)
                .orElseThrow(() -> new IllegalArgumentException(studyGroupId + "에 해당하는 스터디가 없습니다."));

        studyGroup.getSkills().clear();
        parseSkillJson(studyGroupRequest, studyGroupId);

        studyGroup.update(studyGroupRequest.getTitle(), studyGroupRequest.getContent(), studyGroupRequest.getStudyType(),
                studyGroupRequest.getNumberOfMembers(),studyGroupRequest.getLocation(), studyGroupRequest.getDuration(), studyGroupRequest.getStudyStartDate(),
                studyGroupRequest.getMeetingType(), studyGroupRequest.getContactType());
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

    void parseSkillJson(StudyGroupRequest studyGroupRequest, Long studyGroupId) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(studyGroupRequest.getSkills().toString());

        Set<Skill> skills = new HashSet<>();
        for (Object arr : array) {
            JSONObject jsonObj = (JSONObject) arr;
            String title = (String) jsonObj.get("value");
            Skill skill = skillRepository.findByTitle(title).orElseGet(() -> skillRepository.save(Skill.builder()
                    .title(title)
                    .build()));
            skills.add(skill);
            studyGroupRequest.setSkills(skills);
            addSkill(skill, studyGroupId);
        }
    }

    @Override
    public void addSkill(Skill skill, Long studyGroupId) {
        Optional<StudyGroup> studyGroupById = studyGroupRepository.findById(studyGroupId);
        studyGroupById.ifPresent(a -> a.getSkills().add(skill));
    }

    @Override
    public Set<Skill> getSkills(Long studyGroupId) {
        Optional<StudyGroup> studyGroupById = studyGroupRepository.findById(studyGroupId);
        return studyGroupById.orElseThrow().getSkills();
    }

    @Override
    public List<StudyGroupResponse> studyCreatedByMe(String nickname) {
        return studyGroupRepository.findByCreatedBy(nickname)
                .stream()
                .map(StudyGroupResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudyGroupResponse> joinedStudy(User user) {
        List<UserGroup> userGroupList = userGroupRepository.findByUserId(user.getId());
        List<StudyGroup> studyGroupList = new ArrayList<>();
        for(UserGroup userGroup : userGroupList){
            StudyGroup studyGroup = userGroup.getStudyGroup();
            if(!studyGroup.isClosed()) {
                studyGroupList.add(userGroup.getStudyGroup());
            }
        }
        return studyGroupList.stream()
                .map(StudyGroupResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudyGroupResponse> closedStudy(User user) {
        List<UserGroup> userGroupList = userGroupRepository.findByUserId(user.getId());
        List<StudyGroup> studyGroupList = new ArrayList<>();
        for(UserGroup userGroup : userGroupList){
            StudyGroup studyGroup = userGroup.getStudyGroup();
            if(studyGroup.isClosed()) {
                studyGroupList.add(userGroup.getStudyGroup());
            }
        }
        return studyGroupList.stream()
                .map(StudyGroupResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudyGroupResponse> enrolledStudy(User user) {
        List<Enrollment> enrollmentList = enrollmentRepository.findByUser(user);
        List<StudyGroup> studyGroupList = new ArrayList<>();
        for(Enrollment enrollment : enrollmentList){
            if(!enrollment.isAccepted()){
                studyGroupList.add(enrollment.getUserGroup().getStudyGroup());
            }
        }
        return studyGroupList.stream()
                .map(StudyGroupResponse::fromEntity)
                .collect(Collectors.toList());

    }

    @Override
    public List<StudyGroupResponse> getOpenStudyGroup(String sortValue) {
        return studyGroupRepository.findAll(Sort.by(Sort.Direction.DESC, sortValue))
                .stream()
                .filter(studyGroup -> !studyGroup.isClosed())
                .map(StudyGroupResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudyGroupResponse> getClosedStudyGroup(String sortValue) {
        return studyGroupRepository.findAll(Sort.by(Sort.Direction.DESC, sortValue))
                .stream()
                .filter(studyGroup -> studyGroup.isClosed())
                .map(StudyGroupResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public int updateView(Long id) {
        return studyGroupRepository.updateView(id);
    }

    @Override
    public void deleteStudyGroup(User user, Long studyGroupId) {
        StudyGroup studyGroup = validateDeleteStudyGroup(user, studyGroupId);
        studyGroupRepository.delete(studyGroup);

    }
}
