package com.project.community.domain.user.repository;

import com.project.community.domain.user.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    UserGroup findByStudyGroupId(Long studyGroupId);
}
