package com.surfer.apiserver.api.auth.service;


import org.springframework.security.core.userdetails.UserDetailsService;

import static com.surfer.apiserver.api.auth.dto.AuthDTO.*;

public interface AuthService extends UserDetailsService {
    void signUp(SignUpRequest signUpRequest);
    TokenInfo signIn(SignInRequest signInRequest);

//    Page<UserResponse> getUsers(Pageable pageable);
}