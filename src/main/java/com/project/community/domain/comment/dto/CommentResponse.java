package com.project.community.domain.comment.dto;

import com.project.community.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class CommentResponse {

    private Long id;
    private String comment;
    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private String nickname;
    private Long studyGroupId;

    // Entity -> Dto
    public CommentResponse(Comment comment){
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
        this.nickname = comment.getUser().getNickname();
        this.studyGroupId = comment.getStudyGroup().getId();
    }
}
