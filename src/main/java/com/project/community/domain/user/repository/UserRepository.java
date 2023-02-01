package com.project.community.domain.user.repository;

import com.project.community.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    User findByEmail(String email);

    User findByNickname(String nickname);

}