package com.project.community.domain.user.service.impl;

import com.project.community.domain.skill.entity.Skill;
import com.project.community.domain.skill.repository.SkillRepository;
import com.project.community.domain.user.dto.Profile;
import com.project.community.domain.user.dto.UserAccount;
import com.project.community.domain.user.dto.SignUpForm;
import com.project.community.domain.user.entity.User;
import com.project.community.domain.user.repository.UserRepository;
import com.project.community.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final SkillRepository skillRepository;
    @Override
    public void login(User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(user),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public User saveNewUser(@Valid SignUpForm signUpForm) {
        User newUser = User.builder()
                .email(signUpForm.getEmail())
                .nickname(signUpForm.getNickname())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public void updateProfile(User user, Profile profile) throws ParseException {
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        user.getSkills().clear();
        parseSkillJson(profile, user.getId());
        user.update(profile.getNickname(), profile.getLocation(), profile.getPassword());
    }

    @Override
    public Set<Skill> getSkills(Long userId) {
        Optional<User> userById = userRepository.findById(userId);
        return userById.orElseThrow().getSkills();
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String emailOrNickname) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(emailOrNickname);
        if(user== null){
            user= userRepository.findByNickname(emailOrNickname);
        }

        if(user==null){
            throw new UsernameNotFoundException(emailOrNickname);
        }
        return new UserAccount(user);
    }

    void parseSkillJson(Profile profile, Long userId) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(profile.getSkills().toString());

        Set<Skill> skills = new HashSet<>();
        for (Object arr : array) {
            JSONObject jsonObj = (JSONObject) arr;
            String title = (String) jsonObj.get("value");
            Skill skill = skillRepository.findByTitle(title).orElseGet(() -> skillRepository.save(Skill.builder()
                    .title(title)
                    .build()));
            skills.add(skill);
            profile.setSkills(skills);
            addSkill(skill, userId);
        }
    }

    public void addSkill(Skill skill, Long userId) {
        Optional<User> userById = userRepository.findById(userId);
        userById.ifPresent(a -> a.getSkills().add(skill));
    }
}
