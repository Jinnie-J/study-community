package com.project.community.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.community.common.CurrentUser;
import com.project.community.domain.location.entity.Location;
import com.project.community.domain.location.service.LocationService;
import com.project.community.domain.skill.entity.Skill;
import com.project.community.domain.skill.service.SkillService;
import com.project.community.domain.study.dto.StudyGroupResponse;
import com.project.community.domain.study.service.StudyGroupService;
import com.project.community.domain.user.SignUpFormValidator;
import com.project.community.domain.user.dto.Profile;
import com.project.community.domain.user.dto.SignUpForm;
import com.project.community.domain.user.entity.User;
import com.project.community.domain.user.repository.UserRepository;
import com.project.community.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final SignUpFormValidator signUpFormValidator;
    private final UserRepository userRepository;
    private final SkillService skillService;
    private final LocationService locationService;
    private final ObjectMapper objectMapper;

    private final StudyGroupService studyGroupService;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/")
    public String home(@CurrentUser User user, Model model){
        if (user != null){
            model.addAttribute(user);
        }
        List<StudyGroupResponse> openStudyGroupList = studyGroupService.getOpenStudyGroup("id");
        model.addAttribute("studyGroupList", openStudyGroupList);
        model.addAttribute("checked",true);
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model){
        model.addAttribute("signUpForm", new SignUpForm());
        return "user/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors) {
        if (errors.hasErrors()) {
            return "user/sign-up";
        }
        User user = userService.saveNewUser(signUpForm);
        userService.login(user);
        return "redirect:/";
    }

    @GetMapping("/user/{nickname}")
    public String viewProfile(@PathVariable String nickname, Model model, @CurrentUser User user){
        User userByNickname = userRepository.findByNickname(nickname);
        if(nickname == null){
            throw new IllegalArgumentException(nickname + "에 해당하는 사용자가 없습니다.");
        }
        model.addAttribute("user",userByNickname);
        model.addAttribute("isOwner", userByNickname.equals(user));
        return "user/profile";
    }

    @GetMapping("/profile/update")
    public String profileUpdateForm(Model model, @CurrentUser User user) throws JsonProcessingException {
        model.addAttribute("user",user);
        model.addAttribute("profile", new Profile(user));

        Set<Skill> skills = userService.getSkills(user.getId());
        model.addAttribute("skills", skills.stream().map(Skill::getTitle).collect(Collectors.toList()));

        List<String> allSkills = skillService.getAllSkills();
        model.addAttribute("skillList", objectMapper.writeValueAsString(allSkills));

        List<Location> allLocations = locationService.findAll();
        model.addAttribute("locationList", allLocations);
        return "user/profile-modify-form";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@CurrentUser User user, @Valid @ModelAttribute Profile profile, Errors errors,
                                Model model, RedirectAttributes attributes) throws ParseException {
        if (errors.hasErrors()) {
            model.addAttribute(user);
            return "user/profile-modify-form";
        }
        userService.updateProfile(user, profile);
        attributes.addFlashAttribute("message", "프로필을 수정했습니다.");
        return "redirect:/profile/update";
    }

    @GetMapping("/profile/study-group")
    public String viewProfileStudyGroup(Model model, @CurrentUser User user){

        List<StudyGroupResponse> myStudyGroupList = studyGroupService.studyCreatedByMe(user.getNickname());
        List<StudyGroupResponse> joinedStudyList = studyGroupService.joinedStudy(user);
        List<StudyGroupResponse> closedStudyList = studyGroupService.closedStudy(user);
        List<StudyGroupResponse> enrolledStudyList = studyGroupService.enrolledStudy(user);

        model.addAttribute("myStudyGroupList", myStudyGroupList);
        model.addAttribute("joinedStudyList",joinedStudyList);
        model.addAttribute("closedStudyList",closedStudyList);
        model.addAttribute("enrolledStudyList", enrolledStudyList);
        return "user/profile-study-group";
    }


}
