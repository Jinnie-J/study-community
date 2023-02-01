package com.project.community.domain.study.event;

import com.project.community.domain.study.entity.StudyGroup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StudyUpdateEvent {

    private final StudyGroup studyGroup;

    private final String message;
}
