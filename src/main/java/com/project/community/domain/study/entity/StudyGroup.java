package com.project.community.domain.study.entity;

import com.project.community.domain.user.entity.UserGroup;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private String content;  //내용

    private String studyType; //스터디 유형 (프로젝트, 스터디)

    private String numberOfMembers;  //모집 인원

    private String location; //지역

    private String duration;  //기간

    private String online;  //온라인 여부

    private LocalDateTime studyStartDate;  //시작일

    private LocalDateTime createDate;  //등록일

    private LocalDateTime updateDate;  //수정일

    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL)
    private List<UserGroup> userGroups = new ArrayList();

    @Builder
    public StudyGroup(Long id, String title, String content, String studyType, String numberOfMembers, String location,
                      String duration, String online, LocalDateTime studyStartDate, LocalDateTime createDate, LocalDateTime updateDate){
        this.id = id;
        this.title = title;
        this.content = content;
        this.studyType = studyType;
        this.numberOfMembers = numberOfMembers;
        this.location = location;
        this.duration = duration;
        this.online = online;
        this.studyStartDate = studyStartDate;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

}
