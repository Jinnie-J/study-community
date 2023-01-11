package com.project.community.domain.comment.controller;

import com.project.community.common.CurrentUser;
import com.project.community.domain.comment.dto.CommentRequest;
import com.project.community.domain.comment.service.CommentService;
import com.project.community.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/study-group/{studyGroupId}/comments")
    public String createComment(@PathVariable("studyGroupId") Long studyGroupId,
                                @Valid CommentRequest commentRequest, @CurrentUser User user){
        commentService.createComment(user, studyGroupId, commentRequest);
        return "redirect:/study-group/{studyGroupId}";
    }
}
