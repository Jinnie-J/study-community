package com.project.community.domain.study.entity;

import com.project.community.domain.location.entity.Location;
import com.project.community.domain.skill.entity.Skill;
import com.project.community.domain.user.entity.UserGroup;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class StudyGroup {

    @Id
    @GeneratedValue
    @Column(name = "study_group_id")
    private Long id;

    @Column(nullable = false)
    private String title;  //제목

    private String createdBy; //작성자

    private String content;  //내용

    private String studyType; //스터디 유형 (프로젝트, 스터디)

    private String numberOfMembers;  //모집 인원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location; //지역

    private String duration;  //기간

    private String online;  //온라인 여부

    @ManyToMany
    private Set<Skill> skills;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime studyStartDate;  //시작일

    private LocalDateTime createDate;  //등록일

    private LocalDateTime updateDate;  //수정일

    private boolean closed; // 모집마감

    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL)
    private List<UserGroup> userGroups = new ArrayList();

    public void close(){
        this.closed = true;
    }

    public void update(String title, String content, String studyType, String numberOfMembers,
                       Location location, String duration, LocalDateTime studyStartDate, String online){
        this.title = title;
        this.content = content;
        this.studyType = studyType;
        this.numberOfMembers = numberOfMembers;
        this.location = location;
        this.duration = duration;
        this.studyStartDate = studyStartDate;
        this.online = online;
    }
    @Builder
    public StudyGroup(Long id, String title, String createdBy, String content, String studyType, String numberOfMembers, Location location,
                      String duration, String online, Set skills, LocalDateTime studyStartDate, LocalDateTime createDate, LocalDateTime updateDate, Boolean closed){
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.studyType = studyType;
        this.numberOfMembers = numberOfMembers;
        this.location = location;
        this.duration = duration;
        this.online = online;
        this.skills = skills;
        this.studyStartDate = studyStartDate;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.closed = closed;
    }

}
