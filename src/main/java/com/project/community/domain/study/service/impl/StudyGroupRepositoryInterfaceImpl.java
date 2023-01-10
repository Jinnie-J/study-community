package com.project.community.domain.study.service.impl;

import com.project.community.domain.location.entity.QLocation;
import com.project.community.domain.skill.entity.QSkill;
import com.project.community.domain.study.entity.QStudyGroup;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.study.service.StudyGroupRepositoryInterface;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class StudyGroupRepositoryInterfaceImpl extends QuerydslRepositorySupport implements StudyGroupRepositoryInterface {

    public StudyGroupRepositoryInterfaceImpl() {
        super(StudyGroup.class);
    }

    @Override
    public Page<StudyGroup> findByKeyword(String keyword, Pageable pageable) {
        QStudyGroup studyGroup = QStudyGroup.studyGroup;
        JPQLQuery<StudyGroup> query = from(studyGroup).where(studyGroup.closed.isFalse()
                .and(studyGroup.title.containsIgnoreCase(keyword))
                .or(studyGroup.skills.any().title.containsIgnoreCase(keyword))
                .or(studyGroup.location.localNameOfCity.containsIgnoreCase(keyword)))
                .leftJoin(studyGroup.skills, QSkill.skill).fetchJoin()
                .leftJoin(studyGroup.location, QLocation.location).fetchJoin()
                .distinct();
        JPQLQuery<StudyGroup> pageableQuery = getQuerydsl().applyPagination(pageable, query);
        QueryResults<StudyGroup> fetchResults = pageableQuery.fetchResults();
        return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());
    }
}
