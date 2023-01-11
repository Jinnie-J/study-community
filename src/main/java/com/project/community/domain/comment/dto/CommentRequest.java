package com.project.community.domain.comment.dto;

import com.project.community.domain.comment.entity.Comment;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CommentRequest {

    private Long id;

    private String comment;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private StudyGroup studyGroup;

    private User user;

    // Dto -> Entity
    public Comment toEntity(){
        return Comment.builder()
                .id(id)
                .comment(comment)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .studyGroup(studyGroup)
                .user(user)
                .build();
    }
}
