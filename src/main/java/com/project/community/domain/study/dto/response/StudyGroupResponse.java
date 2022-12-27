package com.project.community.domain.study.dto.response;

import com.project.community.domain.study.entity.StudyGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class StudyGroupResponse {

    private Long studyGroupId;
    private Long userId;

    @NotBlank
    private String title;

    private String content;

    private String createdBy;

    private String studyType;

    private String numberOfMembers;

    private String location;

    private String duration;

    private String online;

    private LocalDateTime studyStartDate;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private Boolean closed;

    public static StudyGroupResponse fromEntity(StudyGroup studyGroup){
        return StudyGroupResponse.builder()
                .studyGroupId(studyGroup.getId())
                .title(studyGroup.getTitle())
                .content(studyGroup.getContent())
                .createdBy(studyGroup.getCreatedBy())
                .studyType(studyGroup.getStudyType())
                .numberOfMembers(studyGroup.getNumberOfMembers())
                .location(studyGroup.getLocation())
                .duration(studyGroup.getDuration())
                .online(studyGroup.getOnline())
                .studyStartDate(studyGroup.getStudyStartDate())
                .closed(studyGroup.isClosed())
                .build();
    }
}
