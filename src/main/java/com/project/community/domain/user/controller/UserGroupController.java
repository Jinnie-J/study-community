package com.project.community.domain.user.controller;

import com.project.community.common.CurrentUser;
import com.project.community.domain.enrollment.entity.Enrollment;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.user.entity.User;
import com.project.community.domain.user.entity.UserGroup;
import com.project.community.domain.user.repository.UserGroupRepository;
import com.project.community.domain.user.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserGroupController {

    private final UserGroupRepository userGroupRepository;
    private final UserGroupService userGroupService;

    //스터디 그룹 참가 신청
    @PostMapping("/study-group/{studyGroupId}/enroll")
    public String newEnrollment(@CurrentUser User user, @PathVariable("studyGroupId")Long studyGroupId){

        UserGroup userGroup = userGroupRepository.findByStudyGroupId(studyGroupId);
        userGroupService.newEnrollment(userGroup, user);
        return "redirect:/study-group/{studyGroupId}";
    }

    //스터디 그룹 참가 신청 취소
    @PostMapping("/study-group/{studyGroupId}/disenroll")
    public String cancelEnrollment(@CurrentUser User user, @PathVariable("studyGroupId") Long studyGroupId){
        UserGroup userGroup = userGroupRepository.findByStudyGroupId(studyGroupId);
        userGroupService.cancelEnrollment(userGroup, user);
        return "redirect:/study-group/{studyGroupId}";
    }

    //참가 신청 수락
    @GetMapping("/study-group/{studyGroupId}/user-group/{userGroupId}/enrollments/{enrollmentId}/accept")
    public String acceptEnrollment(@PathVariable("studyGroupId") StudyGroup studyGroup,
                                   @PathVariable("userGroupId") UserGroup userGroup,
                                   @PathVariable("enrollmentId") Enrollment enrollment){
        userGroupService.acceptEnrollment(studyGroup, userGroup,enrollment);
        return "redirect:/study-group/{studyGroupId}/people";
    }

    //참가 신청 거절
    @GetMapping("/study-group/{studyGroupId}/user-group/{userGroupId}/enrollments/{enrollmentId}/reject")
    public String rejectEnrollment(@PathVariable("studyGroupId") StudyGroup studyGroup,
                                   @PathVariable("userGroupId") UserGroup userGroup,
                                   @PathVariable("enrollmentId") Enrollment enrollment){
        userGroupService.rejectEnrollment(studyGroup, userGroup,enrollment);
        return "redirect:/study-group/{studyGroupId}/people";
    }
}
