package com.project.community.domain.study.service;

import com.project.community.domain.study.entity.StudyGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface StudyGroupRepositoryInterface {

    Page<StudyGroup> findByKeyword(String keyword, Pageable pageable);
}
