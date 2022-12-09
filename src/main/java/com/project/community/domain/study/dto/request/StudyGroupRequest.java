package com.project.community.domain.study.dto.request;

import com.project.community.domain.study.entity.StudyGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StudyGroupRequest {

    @NotBlank
    private String title;

    private String content;

    private String studyType;

    private String numberOfMembers;

    private String location;

    private String duration;

    private String online;

    private LocalDateTime studyStartDate;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;


    public static StudyGroup toEntity(StudyGroupRequest studyGroupRequest){
        return StudyGroup.builder()
                .title(studyGroupRequest.getTitle())
                .content(studyGroupRequest.getContent())
                .studyType(studyGroupRequest.getStudyType())
                .numberOfMembers(studyGroupRequest.getNumberOfMembers())
                .location(studyGroupRequest.getLocation())
                .duration(studyGroupRequest.getDuration())
                .online(studyGroupRequest.getOnline())
                .studyStartDate(studyGroupRequest.getStudyStartDate())
                .build();
    }


}
