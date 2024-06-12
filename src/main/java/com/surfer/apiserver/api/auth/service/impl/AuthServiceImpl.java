package com.surfer.apiserver.api.auth.service.impl;

import com.surfer.apiserver.api.auth.dto.AuthDTO.SignInRequest;
import com.surfer.apiserver.api.auth.dto.AuthDTO.SignUpRequest;
import com.surfer.apiserver.api.auth.dto.AuthDTO.TokenInfo;
import com.surfer.apiserver.api.auth.service.AuthService;
import com.surfer.apiserver.common.constant.CommonCode;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.jwt.JwtTokenProvider;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.util.AES256Util;
import com.surfer.apiserver.domain.database.entity.MemberAuthorityEntity;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.repository.MemberAuthorityRepository;
import com.surfer.apiserver.domain.database.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("AuthService")
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService, UserDetailsService {


    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberAuthorityRepository memberAuthorityRepository;


    @Override
    @Transactional
    public void signUp(SignUpRequest signUpRequest) {

            MemberEntity memberEntity = memberRepository.save(MemberEntity.builder()
                    .email(signUpRequest.getEmail())
                    .password(passwordEncoder.encode(signUpRequest.getPassword()))
                    .nickname(signUpRequest.getNickname())
                    .name(signUpRequest.getName())
                    .status(CommonCode.MemberStatus.USE)
                    .build());

            memberAuthorityRepository.save(
                    MemberAuthorityEntity.builder()
                            .member(memberEntity)
                            .authority(CommonCode.MemberAuthority.ROLE_GENERAL)
                            .build());


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
        MemberEntity memberEntity = memberRepository.findByMemberId(Long.parseLong(seq))
                .orElseThrow(() -> new BusinessException(ApiResponseCode.INVALID_USER_ID, HttpStatus.BAD_REQUEST));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        memberEntity.getMemberAuthorityEntities().forEach(memberAuthority ->
                grantedAuthorities.add(new SimpleGrantedAuthority(memberAuthority.toString())));
        return new User(AES256Util.encrypt(seq), memberEntity.getPassword(), grantedAuthorities);
    }
}