package com.project.community.domain.comment.service;

import com.project.community.domain.comment.dto.CommentRequest;
import com.project.community.domain.user.entity.User;

public interface CommentService {
    Long createComment(User user, Long studyGroupId, CommentRequest commentRequest);

    void update(Long commentId, CommentRequest commentRequest);

    void delete(Long commentId);
}
