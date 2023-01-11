package com.project.community.domain.comment.service.impl;

import com.project.community.domain.comment.dto.CommentRequest;
import com.project.community.domain.comment.entity.Comment;
import com.project.community.domain.comment.repository.CommentRepository;
import com.project.community.domain.comment.service.CommentService;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.study.repository.StudyGroupRepository;
import com.project.community.domain.user.entity.User;
import com.project.community.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final StudyGroupRepository studyGroupRepository;

    @Override
    public void createComment(User user, Long studyGroupId, CommentRequest commentRequest) {
        StudyGroup studyGroup = studyGroupRepository.findById(studyGroupId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다." + studyGroupId));
        commentRequest.setUser(user);
        commentRequest.setStudyGroup(studyGroup);

        Comment comment = commentRequest.toEntity();
        commentRepository.save(comment);
    }
}
