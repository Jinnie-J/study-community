package com.project.community.domain.study.dto;

import com.project.community.domain.comment.dto.CommentResponse;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    private Long remainingSeats;

    private Location location;

    private String duration;

    private MeetingType meetingType;

    private ContactType contactType;

    private Set<Skill> skills;

    private LocalDateTime studyStartDate;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private Boolean closed;

    private List<CommentResponse> comments;

    private int view;

    public static StudyGroupResponse fromEntity(StudyGroup studyGroup){
        return StudyGroupResponse.builder()
                .studyGroupId(studyGroup.getId())
                .title(studyGroup.getTitle())
                .content(studyGroup.getContent())
                .createdDate(studyGroup.getCreatedDate())
                .createdBy(studyGroup.getCreatedBy())
                .studyType(studyGroup.getStudyType())
                .numberOfMembers(studyGroup.getNumberOfMembers())
                .location(studyGroup.getLocation())
                .duration(studyGroup.getDuration())
                .meetingType(studyGroup.getMeetingType())
                .contactType(studyGroup.getContactType())
                .skills(studyGroup.getSkills())
                .remainingSeats(studyGroup.getRemainingSeats())
                .studyStartDate(studyGroup.getStudyStartDate())
                .closed(studyGroup.isClosed())
                .view(studyGroup.getView())
                .comments(studyGroup.getComments().stream().map(CommentResponse::new).collect(Collectors.toList()))
                .build();
    }
}
