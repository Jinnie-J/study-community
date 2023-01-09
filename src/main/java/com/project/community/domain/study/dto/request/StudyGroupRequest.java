package com.project.community.domain.study.dto.request;

import com.project.community.domain.location.entity.Location;
import com.project.community.domain.study.entity.StudyGroup;
import com.project.community.domain.study.enums.ContactType;
import com.project.community.domain.study.enums.MeetingType;
import com.project.community.domain.study.enums.StudyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StudyGroupRequest implements Cloneable{

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

    private Object skills;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime studyStartDate;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private boolean closed;


    public static StudyGroup toEntity(StudyGroupRequest studyGroupRequest){
        return StudyGroup.builder()
                .title(studyGroupRequest.getTitle())
                .content(studyGroupRequest.getContent())
                .createdBy(studyGroupRequest.getCreatedBy())
                .createDate(studyGroupRequest.getCreateDate())
                .studyType(studyGroupRequest.getStudyType())
                .numberOfMembers(studyGroupRequest.getNumberOfMembers())
                .remainingSeats(studyGroupRequest.getRemainingSeats())
                .location(studyGroupRequest.getLocation())
                .duration(studyGroupRequest.getDuration())
                .meetingType(studyGroupRequest.getMeetingType())
                .skills((Set) studyGroupRequest.getSkills())
                .studyStartDate(studyGroupRequest.getStudyStartDate())
                .contactType(studyGroupRequest.getContactType())
                .closed(studyGroupRequest.isClosed())
                .build();
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }


}
