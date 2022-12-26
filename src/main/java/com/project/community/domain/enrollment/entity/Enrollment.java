package com.project.community.domain.enrollment.entity;

import com.project.community.domain.user.entity.User;
import com.project.community.domain.user.entity.UserGroup;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Enrollment {

    @Id @GeneratedValue
    @Column(name = "enrollment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_grou_id")
    private UserGroup userGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime enrolledAt;

    private boolean accepted;

    //private boolean attended;

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public void setAccepted(Boolean accepted){
        this.accepted = accepted;
    }

    @Builder
    public Enrollment(Long id, User user, UserGroup userGroup, LocalDateTime enrolledAt, boolean accepted){
        this.id = id;
        this.user = user;
        this.userGroup = userGroup;
        this.enrolledAt = enrolledAt;
        this.accepted = accepted;
    }
}
