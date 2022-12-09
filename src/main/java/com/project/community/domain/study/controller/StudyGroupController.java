package com.project.community.domain.study.controller;

import com.project.community.common.CurrentUser;
import com.project.community.domain.study.dto.request.StudyGroupRequest;
import com.project.community.domain.study.service.StudyGroupService;
import com.project.community.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    //스터디그룹 작성 화면 조회
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


}
