package com.project.community.domain.study.dto.response;

import com.project.community.domain.location.entity.Location;
import com.project.community.domain.skill.entity.Skill;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.study.enums.ContactType;
import com.project.community.domain.study.enums.MeetingType;
import com.project.community.domain.study.enums.StudyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

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

    private StudyType studyType;

    private String numberOfMembers;

    private Location location;

    private String duration;

    private MeetingType meetingType;

    private ContactType contactType;

    private Set<Skill> skills;

    private LocalDateTime studyStartDate;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private Boolean closed;

    public static StudyGroupResponse fromEntity(StudyGroup studyGroup){
        return StudyGroupResponse.builder()
                .studyGroupId(studyGroup.getId())
                .title(studyGroup.getTitle())
                .content(studyGroup.getContent())
                .createDate(studyGroup.getCreateDate())
                .createdBy(studyGroup.getCreatedBy())
                .studyType(studyGroup.getStudyType())
                .numberOfMembers(studyGroup.getNumberOfMembers())
                .location(studyGroup.getLocation())
                .duration(studyGroup.getDuration())
                .meetingType(studyGroup.getMeetingType())
                .contactType(studyGroup.getContactType())
                .skills(studyGroup.getSkills())
                .studyStartDate(studyGroup.getStudyStartDate())
                .closed(studyGroup.isClosed())
                .build();
    }
}
