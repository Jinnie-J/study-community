package com.project.community.domain.comment.entity;


import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @EqualsAndHashCode(of ="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String comment;

    private String createdDate;

    private String modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id")
    private StudyGroup studyGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public void update(String comment){
        this.comment = comment;
    }
}