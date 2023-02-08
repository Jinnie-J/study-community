package com.project.community.domain.study;

import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.study.repository.StudyGroupRepository;
import com.project.community.domain.study.service.StudyGroupService;
import com.project.community.domain.user.dto.SignUpForm;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Slf4j
public class StudyGroupControllerTest {

    @Autowired protected MockMvc mockMvc;
    @Autowired protected StudyGroupService  studyGroupService;
    @Autowired protected StudyGroupRepository studyGroupRepository;
    @Autowired protected UserService userService;

    @BeforeEach
    void beforeEach(){
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setNickname("jinnie");
        signUpForm.setEmail("jinnie@email.com");
        signUpForm.setPassword("12345678");
        userService.saveNewUser(signUpForm);
    }
    @WithUserDetails(value="jinnie", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("스터디 그룹 개설 폼 조회")
    void createdForm() throws Exception{
        mockMvc.perform(get("/study-group/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("study/study-group-form"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("studyGroupRequest"));
    }

    @WithUserDetails(value="jinnie", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("스터디 그룹 개설 - 성공")
    void creaStudy_success() throws Exception{
        mockMvc.perform(post("/study-group/create")
                .param("id", "999")
                .param("title","study title")
                .param("content","study content")
                .param("duration","3개월")
                .param("studyType","스터디")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/study-group"));

        Optional<StudyGroup> studyGroup = studyGroupRepository.findById(999L);
        assertNotNull(studyGroup);
    }

    @WithUserDetails(value="jinnie", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("스터디 그룹 개설 - 실패(제목 입력 하지 않은 경우)")
    void createStudy_fail() throws Exception{
        mockMvc.perform(post("/study-group/create")
                .param("id","999")
                .param("content","study content")
                .param("duration","3개월")
                .param("studyType","스터디")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("study/study-group-form"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("user"));
        Optional<StudyGroup> studyGroup = studyGroupRepository.findById(999L);
        assertFalse(studyGroup.isPresent());
    }

    @WithUserDetails(value="jinnie", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("스터디 그룹 전체 조회")
    void getAllStudyGroup() throws Exception{
        mockMvc.perform(get("/study-group"))
                .andExpect(status().isOk())
                .andExpect(view().name("study/study-group"))
                .andExpect(model().attributeExists("studyGroupList"));
    }

    @WithUserDetails(value="jinnie", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("스터디 그룹 상세 조회")
    void getStudyGroup() throws Exception{
        mockMvc.perform(get("/study-group/18"))
                .andExpect(status().isOk())
                .andExpect(view().name("study/study-group-detail"))
                .andExpect(model().attributeExists("studyGroup"))
                .andExpect(model().attributeExists("userGroup"));
    }

    @WithUserDetails(value="jinnie", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("스터디 그룹 수정 폼 조회")
    void updateForm() throws Exception{
        mockMvc.perform(get("/study-group/update/18"))
                .andExpect(status().isOk())
                .andExpect(view().name("study/study-group-modify-form"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("studyGroup"));
    }

    @WithUserDetails(value="jinnie", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("스터디 그룹 수정 - 성공")
    void updateStudyGroup() throws Exception{

        StudyGroup studyGroup = studyGroupRepository.findById(18L).orElseThrow(IllegalArgumentException::new);
        String titleBeforeUpdate = studyGroup.getTitle();

        mockMvc.perform(post("/study-group/update/18")
                        .param("title","update title")
                        .param("content","update content")
                        .param("duration","3개월")
                        .param("studyType","스터디")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/study-group/18"))
                .andExpect(flash().attributeExists("message"));

        StudyGroup newStudyGroup = studyGroupRepository.findById(18L).orElseThrow(IllegalArgumentException::new);
        String titleAfterUpdate = newStudyGroup.getTitle();

        assertThat(titleBeforeUpdate).isNotEqualTo(titleAfterUpdate);
    }

    @WithUserDetails(value="jinnie", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("스터디 그룹 수정 - 실패")
    void updateStudyGroup_fail() throws Exception {

        mockMvc.perform(post("/study-group/update/18")
                .param("content","update content")
                .param("duration","3개월")
                .param("studyType","스터디")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/study-group/update/18"))
                .andExpect(flash().attributeExists("message"));
    }

    @WithUserDetails(value="jinnie", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("스터디 그룹 모집 마감")
    void closeStudyGroup() throws Exception{

        StudyGroup studyGroup = studyGroupRepository.findById(18L).orElseThrow(IllegalArgumentException::new);
        mockMvc.perform(post("/study-group/close/18")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/study-group/18"))
                .andExpect(flash().attributeExists("message"));

        assertTrue(studyGroup.isClosed());
    }

    @WithUserDetails(value="jinnie", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("스터디 그룹 삭제")
    void deleteStudyGroup() throws Exception{

        mockMvc.perform(get("/study-group/delete/358"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/study-group/sort/id"))
                .andExpect(flash().attributeExists("message"));

        Optional<StudyGroup> studyGroup = studyGroupRepository.findById(358L);
        assertFalse(studyGroup.isPresent());

    }

}
