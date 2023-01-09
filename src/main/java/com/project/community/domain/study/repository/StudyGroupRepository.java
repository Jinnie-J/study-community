package com.project.community.domain.study.repository;


import com.project.community.domain.study.service.StudyGroupRepositoryInterface;
import com.project.community.domain.study.entity.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long>, StudyGroupRepositoryInterface {
}
