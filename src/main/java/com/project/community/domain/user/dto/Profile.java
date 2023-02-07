package com.project.community.domain.user.dto;

import com.project.community.domain.location.entity.Location;
import com.project.community.domain.skill.entity.Skill;
import com.project.community.domain.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@NoArgsConstructor
public class Profile {

    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    private String nickname;

    private Location location;

    private Object skills;

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;

    public Profile(User user){
        this.nickname = user.getNickname();
        this.location = user.getLocation();
        this.skills = user.getSkills();
        this.password = user.getPassword();
    }


}
