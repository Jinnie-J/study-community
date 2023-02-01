package com.project.community.domain.study.event;

import com.project.community.domain.study.entity.StudyGroup;
import lombok.Getter;

@Getter
public class StudyCreatedEvent{
    private StudyGroup studyGroup;

    public StudyCreatedEvent(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }
}
