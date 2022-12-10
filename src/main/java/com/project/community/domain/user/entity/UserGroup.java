package com.project.community.domain.user.entity;

import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.user.UserType;
import lombok.*;

import javax.persistence.*;

@Entity @Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class UserGroup {
    @Id
    @GeneratedValue
    @Column(name = "user_group_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id")
    private StudyGroup studyGroup;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Builder
    public UserGroup(Long id , User user, StudyGroup studyGroup, UserType userType){
        this.id = id;
        this.user = user;
        this.studyGroup = studyGroup;
        this.userType = userType;
    }
}
