package com.project.community.domain.study.controller;

import com.project.community.common.CurrentUser;
import com.project.community.domain.study.dto.request.StudyGroupRequest;
import com.project.community.domain.study.dto.response.StudyGroupResponse;
import com.project.community.domain.study.service.StudyGroupService;
import com.project.community.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    //스터디 그룹 작성 화면 조회
    @GetMapping("/study-group/new")
    public String createStudyGroupForm(@CurrentUser User user, Model model){
        model.addAttribute(user);
        model.addAttribute(new StudyGroupRequest());
        return "study/study-group-form";
    }

    //스터디 그룹 등록
    @PostMapping("/study-group/create")
    public String createStudyGroup(StudyGroupRequest studyGroupRequest, @CurrentUser User user){
        studyGroupService.createStudyGroup(user, studyGroupRequest);

        return "redirect:/study-group/new";
    }

    //스터디 그룹 전체 조회
    @GetMapping("/study-group")
    public String getAllStudyGroup(Model model){
        List<StudyGroupResponse> studyGroupList = studyGroupService.getAllStudyGroup();
        model.addAttribute("studyGroupList", studyGroupList);
        return "study/study-group";
    }

}
