package com.surfer.apiserver.api.auth.service;


import static com.surfer.apiserver.api.auth.dto.AuthDTO.*;

public interface AuthService {
    void signUp(SignUpRequest signUpRequest);

    TokenInfo signInByEmailAndPassword(SignInRequest signInRequest);

    TokenInfo signInByRefreshToken(String refreshToken);
//    Page<`UserResponse> getUsers(Pageable pageable);
}