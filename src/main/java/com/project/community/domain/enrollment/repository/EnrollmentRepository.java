package com.project.community.domain.enrollment.repository;

import com.project.community.domain.enrollment.entity.Enrollment;
import com.project.community.domain.user.entity.User;
import com.project.community.domain.user.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByUserGroupAndUser(UserGroup userGroup, User user);

    Enrollment findByUserGroupAndUser(UserGroup userGroup, User user);
}
