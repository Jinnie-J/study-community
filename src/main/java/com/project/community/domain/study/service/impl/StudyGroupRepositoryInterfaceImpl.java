package com.project.community.domain.study.service.impl;

import com.project.community.domain.study.entity.QStudyGroup;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.study.service.StudyGroupRepositoryInterface;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class StudyGroupRepositoryInterfaceImpl extends QuerydslRepositorySupport implements StudyGroupRepositoryInterface {

    public StudyGroupRepositoryInterfaceImpl() {
        super(StudyGroup.class);
    }

    @Override
    public List<StudyGroup> findByKeyword(String keyword) {
        QStudyGroup studyGroup = QStudyGroup.studyGroup;
        JPQLQuery<StudyGroup> query = from(studyGroup).where(studyGroup.closed.isFalse()
                .and(studyGroup.title.containsIgnoreCase(keyword))
                .or(studyGroup.skills.any().title.containsIgnoreCase(keyword))
                .or(studyGroup.location.localNameOfCity.containsIgnoreCase(keyword)));
    return query.fetch();
    }
}
