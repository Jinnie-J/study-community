package com.project.community.domain.study.service;

import com.project.community.domain.study.entity.StudyGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface StudyGroupRepositoryInterface {

    List<StudyGroup> findByKeyword(String keyword);
}
