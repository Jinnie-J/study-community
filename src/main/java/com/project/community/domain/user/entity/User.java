package com.project.community.domain.user.entity;

import com.project.community.common.BaseTimeEntity;
import com.project.community.domain.location.entity.Location;
import com.project.community.domain.skill.entity.Skill;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToMany
    private Set<Skill> skills;

    @Column(unique = true)
    private String nickname;

    private String password;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserGroup> userGroups = new ArrayList<>();

    public void update(String nickname, Location location, String password){
        this.nickname = nickname;
        this.location = location;
        this.password = password;
    }
    @Builder
    public User(Long id, String email, Location location, Set skills, String nickname, String password, String profileImage){
        this.id = id;
        this.email = email;
        this.location = location;
        this.skills = skills;
        this.nickname = nickname;
        this.password = password;
        this.profileImage = profileImage;
    }

}
