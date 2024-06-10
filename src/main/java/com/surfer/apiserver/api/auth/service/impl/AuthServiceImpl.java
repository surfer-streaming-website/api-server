package com.surfer.apiserver.api.auth.service.impl;

import com.surfer.apiserver.api.auth.dto.AuthDTO.SignInRequest;
import com.surfer.apiserver.api.auth.dto.AuthDTO.SignUpRequest;
import com.surfer.apiserver.api.auth.dto.AuthDTO.TokenInfo;
import com.surfer.apiserver.api.auth.service.AuthService;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.jwt.JwtTokenProvider;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.util.AES256Util;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("AuthService")
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService, UserDetailsService {


    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public void signUp(SignUpRequest signUpRequest) {
        try {
            memberRepository.save(MemberEntity.builder()
                    .email(signUpRequest.getEmail())
                    .password(passwordEncoder.encode(signUpRequest.getPassword()))
                    .role("ROLE_USER")
                    .nickname(signUpRequest.getNickname())
                    .name(signUpRequest.getName())
                    .build());
        } catch (Exception e) {
            log.error(e.getMessage());
            if (e.getMessage().contains("EMAIL NULLS FIRST")) {
                throw new BusinessException(ApiResponseCode.UNIQUE_PARAMETER_VIOLATION_EMAIL, HttpStatus.BAD_REQUEST);
            } else if (e.getMessage().contains("NICKNAME NULLS FIRST")) {
                throw new BusinessException(ApiResponseCode.UNIQUE_PARAMETER_VIOLATION_NICKNAME, HttpStatus.BAD_REQUEST);
            }
        }

    }

    @Override
    public TokenInfo signInByEmailAndPassword(SignInRequest signInRequest) {
        MemberEntity memberEntity = memberRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new BusinessException(ApiResponseCode.FAILED_SIGN_IN_USER, HttpStatus.BAD_REQUEST));
        return jwtTokenProvider.createToken(memberEntity);
    }


    @Override
    public TokenInfo signInByRefreshToken(String refreshToken) {
        MemberEntity memberEntity = memberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.INVALID_API_REFRESH_TOKEN, HttpStatus.UNAUTHORIZED));
        return jwtTokenProvider.createToken(memberEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String seq) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByMemberSeq(Long.parseLong(seq))
                .orElseThrow(() -> new BusinessException(ApiResponseCode.INVALID_USER_ID, HttpStatus.BAD_REQUEST));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(memberEntity.getRole()));
        return new User(AES256Util.encrypt(seq), memberEntity.getPassword(), grantedAuthorities);
    }
}