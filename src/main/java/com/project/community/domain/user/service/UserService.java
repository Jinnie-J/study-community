package com.project.community.domain.user.service;

import com.project.community.domain.skill.entity.Skill;
import com.project.community.domain.user.dto.Profile;
import com.project.community.domain.user.dto.SignUpForm;
import com.project.community.domain.user.entity.User;
import org.json.simple.parser.ParseException;

import javax.validation.Valid;
import java.util.Set;

public interface UserService {

    void login(User user);
    User saveNewUser(@Valid SignUpForm signUpForm);

    void updateProfile(User user, Profile profile) throws ParseException;

    Set<Skill> getSkills(Long userId);
}
