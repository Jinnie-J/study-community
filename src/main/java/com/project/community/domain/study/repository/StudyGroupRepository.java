package com.project.community.domain.study.repository;


import com.project.community.domain.study.service.StudyGroupRepositoryInterface;
import com.project.community.domain.study.entity.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long>, StudyGroupRepositoryInterface {
    List<StudyGroup> findByCreatedBy(String nickname);

    @Modifying
    @Query("update StudyGroup s set s.view = s.view + 1 where s.id = :id")
    int updateView(@Param("id")Long id);
}
