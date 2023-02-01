package com.project.community.domain.skill.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Skill {

    @Id @GeneratedValue
    @Column(name = "skill_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Builder
    public Skill(Long id, String title){
        this.id = id;
        this.title = title;
    }
}
