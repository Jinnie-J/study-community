package com.project.community.domain.comment.service.impl;

import com.project.community.domain.comment.dto.CommentRequest;
import com.project.community.domain.comment.entity.Comment;
import com.project.community.domain.comment.repository.CommentRepository;
import com.project.community.domain.comment.service.CommentService;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.study.repository.StudyGroupRepository;
import com.project.community.domain.user.entity.User;
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
    public Long createComment(User user, Long studyGroupId, CommentRequest commentRequest) {
        StudyGroup studyGroup = studyGroupRepository.findById(studyGroupId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다." + studyGroupId));
        commentRequest.setUser(user);
        commentRequest.setStudyGroup(studyGroup);

        Comment comment = commentRequest.toEntity();
        commentRepository.save(comment);

        return commentRequest.getId();
    }

    @Override
    public void update(Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        comment.update(commentRequest.getComment());
    }

    @Override
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" +commentId));

        commentRepository.delete(comment);
    }
}
