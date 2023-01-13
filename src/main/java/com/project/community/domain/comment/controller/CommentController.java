package com.project.community.domain.comment.controller;

import com.project.community.common.CurrentUser;
import com.project.community.domain.comment.dto.CommentRequest;
import com.project.community.domain.comment.service.CommentService;
import com.project.community.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/study-group/{studyGroupId}/comments")
    public ResponseEntity createComment(@PathVariable("studyGroupId") Long studyGroupId,
                                        @RequestBody CommentRequest commentRequest, @CurrentUser User user){
        return ResponseEntity.ok(commentService.createComment(user, studyGroupId, commentRequest));
    }

    @PutMapping("/study-group/{studyGroupId}/comments/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long commentId, @RequestBody CommentRequest commentRequest){
        commentService.update(commentId, commentRequest);
        return ResponseEntity.ok(commentId);
    }

    @DeleteMapping("/study-group/{studyGroupId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId){
        commentService.delete(commentId);
        return ResponseEntity.ok(commentId);
    }
}
