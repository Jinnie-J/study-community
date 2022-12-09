package com.project.community.domain.user.service.impl;

import com.project.community.domain.user.dto.UserAccount;
import com.project.community.domain.user.dto.request.SignUpForm;
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

import javax.validation.Valid;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

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
}
