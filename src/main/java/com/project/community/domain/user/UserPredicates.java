package com.project.community.domain.user;

import com.project.community.domain.location.entity.Location;
import com.project.community.domain.skill.entity.Skill;
import com.project.community.domain.user.entity.QUser;
import com.querydsl.core.types.Predicate;

import java.util.Set;


public class UserPredicates {

    public static Predicate findByLocationAndSkills(Location location, Set<Skill> skills){
        QUser user = QUser.user;
        return user.location.in(location).and(user.skills.any().in(skills));
    }
}
