package com.surfer.apiserver.api.auth.service.impl;

import com.surfer.apiserver.api.auth.dto.AuthDTO.*;
import com.surfer.apiserver.api.auth.service.AuthService;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.jwt.TokenProvider;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.repository.AuthorityRepository;
import com.surfer.apiserver.domain.database.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service("AuthService")
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {


    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        memberRepository.save(MemberEntity.builder()
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .build());
    }

    @Override
    public TokenInfo signIn(SignInRequest signInRequest) {
        String token = tokenProvider.generateToken(
                memberRepository.findByEmail(signInRequest.getEmail())
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.INVALID_USER_ID, HttpStatus.BAD_REQUEST)),
                Duration.ofHours(1)
        );
        return TokenInfo.builder()
                .accessToken(token)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.INVALID_USER_ID, HttpStatus.BAD_REQUEST));
    }
}