package com.project.community.domain.user;

import com.project.community.domain.enrollment.repository.EnrollmentRepository;
import com.project.community.domain.study.dto.request.StudyGroupRequest;
import com.project.community.domain.study.dto.response.StudyGroupResponse;
import com.project.community.domain.study.service.StudyGroupService;
import com.project.community.domain.user.dto.request.SignUpForm;
import com.project.community.domain.user.entity.User;
import com.project.community.domain.user.entity.UserGroup;
import com.project.community.domain.user.repository.UserGroupRepository;
import com.project.community.domain.user.repository.UserRepository;
import com.project.community.domain.user.service.UserGroupService;
import com.project.community.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Slf4j
public class UserGroupControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired protected UserService userService;
    @Autowired protected StudyGroupService studyGroupService;
    @Autowired protected UserGroupRepository userGroupRepository;
    @Autowired protected UserGroupService userGroupService;
    @Autowired protected UserRepository userRepository;
    @Autowired protected EnrollmentRepository enrollmentRepository;

    @BeforeEach
    void beforeEach(){
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setNickname("test");
        signUpForm.setEmail("test@email.com");
        signUpForm.setPassword("12345678");
        userService.saveNewUser(signUpForm);
    }

    @WithUserDetails(value="test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("스터디 그룹 참가 신청")
    void newEnrollment() throws Exception{
        User leader = userRepository.findByNickname("jieun");
        User member = userRepository.findByNickname("test");
        StudyGroupResponse studyGroup = createStudyGroup("test-study-title","test-study-content", leader);
        UserGroup userGroup = userGroupRepository.findByStudyGroupId(studyGroup.getStudyGroupId());

        mockMvc.perform(post("/study-group/" + studyGroup.getStudyGroupId() + "/enroll")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/study-group/"+ studyGroup.getStudyGroupId()));

        assertTrue(enrollmentRepository.existsByUserGroupAndUser(userGroup, member));
        assertFalse(enrollmentRepository.findByUserGroupAndUser(userGroup,member).isAccepted());
    }

    @WithUserDetails(value="test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("스터디 그룹 참가 신청 취소")
    void cancelEnrollment() throws Exception{
        User leader = userRepository.findByNickname("jieun");
        User member = userRepository.findByNickname("test");
        StudyGroupResponse studyGroup = createStudyGroup("test-study-title","test-study-content", leader);
        UserGroup userGroup = userGroupRepository.findByStudyGroupId(studyGroup.getStudyGroupId());

        userGroupService.newEnrollment(userGroup,member);

        mockMvc.perform(post("/study-group/" + studyGroup.getStudyGroupId() + "/disenroll")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/study-group/"+ studyGroup.getStudyGroupId()));

        assertFalse(enrollmentRepository.existsByUserGroupAndUser(userGroup, member));
    }

    @WithUserDetails(value="test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("스터디 그룹 참가 신청 수락")
    void acceptEnrollment() throws Exception{
        User leader = userRepository.findByNickname("jieun");
        User member = userRepository.findByNickname("test");
        StudyGroupResponse studyGroup = createStudyGroup("test-study-title","test-study-content", leader);
        UserGroup userGroup = userGroupRepository.findByStudyGroupId(studyGroup.getStudyGroupId());

        userGroupService.newEnrollment(userGroup,member);
        Long enrollmentId = enrollmentRepository.findByUserGroupAndUser(userGroup, member).getId();

        mockMvc.perform(get("/study-group/"+ studyGroup.getStudyGroupId() + "/user-group/"+ userGroup.getId() + "/enrollments/"+ enrollmentId + "/accept" )
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/study-group/"+ studyGroup.getStudyGroupId() +"/people"));

        assertTrue(enrollmentRepository.findByUserGroupAndUser(userGroup,member).isAccepted());
    }

    @WithUserDetails(value="jinnie", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("스터디 그룹 참가 신청 거절")
    void rejectEnrollment() throws Exception{
        //거절 시, 해당 그룹에 신청 다시 못하게 할 것 인지 확인
    }

    private StudyGroupResponse createStudyGroup(String title, String content, User user){
        StudyGroupRequest studyGroup= StudyGroupRequest.builder()
                .title(title)
                .content(content)
                .build();
        return studyGroupService.createStudyGroup(user, studyGroup);

    }
}
