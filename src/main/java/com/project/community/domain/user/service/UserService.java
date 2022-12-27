package com.project.community.domain.user.service;

import com.project.community.domain.user.dto.request.SignUpForm;
import com.project.community.domain.user.entity.User;

import javax.validation.Valid;

public interface UserService {

    void login(User user);
    User saveNewUser(@Valid SignUpForm signUpForm);

}
