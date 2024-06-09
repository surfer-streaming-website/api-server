package com.surfer.apiserver.api.auth.service;


import org.springframework.security.core.userdetails.UserDetailsService;

import static com.surfer.apiserver.api.auth.dto.AuthDTO.*;

public interface AuthService {
    void signUp(SignUpRequest signUpRequest);
    TokenInfo signIn(SignInRequest signInRequest);
    TokenInfo signIn(String refreshToken);
//    Page<UserResponse> getUsers(Pageable pageable);
}