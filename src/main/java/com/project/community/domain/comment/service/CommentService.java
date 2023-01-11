package com.project.community.domain.comment.service;

import com.project.community.domain.comment.dto.CommentRequest;
import com.project.community.domain.user.entity.User;

public interface CommentService {
    void createComment(User user, Long studyGroupId, CommentRequest commentRequest);
}
