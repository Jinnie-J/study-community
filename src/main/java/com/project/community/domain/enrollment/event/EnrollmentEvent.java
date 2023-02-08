package com.project.community.domain.enrollment.event;

import com.project.community.domain.enrollment.entity.Enrollment;
import com.project.community.domain.user.entity.UserGroup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EnrollmentEvent {

    private final Enrollment enrollment;

    private final String message;

    private final UserGroup userGroup;
}
