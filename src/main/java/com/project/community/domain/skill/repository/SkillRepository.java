package com.project.community.domain.skill.repository;

import com.project.community.domain.skill.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findByTitle(String title);
}
