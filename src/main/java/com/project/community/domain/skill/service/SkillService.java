package com.project.community.domain.skill.service;

import com.project.community.domain.skill.entity.Skill;
import com.project.community.domain.skill.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public List<String> getAllSkills() {
        return skillRepository.findAll().stream().map(Skill::getTitle).collect(Collectors.toList());
    }
}
