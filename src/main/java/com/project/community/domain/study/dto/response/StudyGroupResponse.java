package com.project.community.domain.study.dto.response;

import com.project.community.domain.study.entity.StudyGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class StudyGroupResponse {

    private Long studyGroupId;
    private Long userId;

    public static StudyGroupResponse fromEntity(StudyGroup studyGroup){
        return StudyGroupResponse.builder()
                .studyGroupId(studyGroup.getId())
                .build();
    }
}
