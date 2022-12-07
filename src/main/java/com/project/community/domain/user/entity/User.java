package com.project.community.domain.user.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String location;

    @Column(unique = true)
    private String nickname;

    private String password;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @Builder
    public User(Long id, String email, String location, String nickname, String password){
        this.id = id;
        this.email = email;
        this.location = location;
        this.nickname = nickname;
        this.password = password;
    }





}
