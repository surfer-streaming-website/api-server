package com.surfer.apiserver.api.auth.service.impl;

import com.surfer.apiserver.api.auth.dto.AuthDTO.*;
import com.surfer.apiserver.api.auth.service.AuthService;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.jwt.JwtTokenProvider;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.util.AES256Util;
import com.surfer.apiserver.domain.database.entity.ArtistApplicationEntity;
import com.surfer.apiserver.domain.database.entity.MemberAuthorityEntity;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.repository.ArtistApplicationRepository;
import com.surfer.apiserver.domain.database.repository.MemberAuthorityRepository;
import com.surfer.apiserver.domain.database.repository.MemberRepository;
import com.surfer.apiserver.domain.database.repository.custom.CustomArtistApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.surfer.apiserver.api.auth.dto.AuthDTO.*;
import static com.surfer.apiserver.common.constant.CommonCode.*;


@Service("AuthService")
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService, UserDetailsService {


    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberAuthorityRepository memberAuthorityRepository;
    private final ArtistApplicationRepository artistApplicationRepository;
    private final CustomArtistApplicationRepository customArtistApplicationRepository;

    @Override
    @Transactional
    public void signUp(SignUpRequest signUpRequest) {

        MemberEntity memberEntity = memberRepository.save(MemberEntity.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .nickname(signUpRequest.getNickname())
                .name(signUpRequest.getName())
                .status(MemberStatus.USE)
                .build());

        memberAuthorityRepository.save(
                MemberAuthorityEntity.builder()
                        .member(memberEntity)
                        .authority(MemberAuthority.ROLE_GENERAL)
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

    @Transactional
    @Override
    public void createArtistApplication(CreateArtistApplicationRequest createArtistApplicationRequest) {
        MemberEntity memberEntity = getMemberEntityByAuthentication();
        artistApplicationRepository.save(ArtistApplicationEntity.builder()
                .locationType(LocationType.valueOf(createArtistApplicationRequest.getLocationType()))
                .sector(Sector.valueOf(createArtistApplicationRequest.getSector()))
                .copyrightName(createArtistApplicationRequest.getCopyrightName())
                .albumName(createArtistApplicationRequest.getAlbumName())
                .artistName(createArtistApplicationRequest.getArtistName())
                .authorName(createArtistApplicationRequest.getAuthorName())
                .status(ArtistApplicationStatus.Pending)
                .member(memberEntity)
                .build());
    }

    @Override
    public Page<GetArtistApplicationsResponse> getArtistApplicationsAsPage(Pageable pageable) {
        return customArtistApplicationRepository
                .findNotDeleteArtistApplicationByMember(pageable, getMemberEntityByAuthentication());
    }

    @Override
    public GetArtistApplicationResponse getArtistApplication(Long id) {
        ArtistApplicationEntity artistApplicationEntity = artistApplicationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.NOT_FOUND, HttpStatus.NOT_FOUND));
        validAuthByMemberId(artistApplicationEntity.getMember().getMemberId());
        return GetArtistApplicationResponse.convertByArtistApplicationEntity(artistApplicationEntity);
    }

    @Transactional
    @Override
    public void updateArtistApplication(UpdateArtistApplicationRequest updateArtistApplicationRequest) {
        ArtistApplicationEntity artistApplicationEntity = artistApplicationRepository.findById(updateArtistApplicationRequest.getArtistApplicationId())
                .orElseThrow(() -> new BusinessException(ApiResponseCode.NOT_FOUND, HttpStatus.NOT_FOUND));
        validAuthByMemberId(artistApplicationEntity.getMember().getMemberId());

        artistApplicationEntity.setLocationType(LocationType.valueOf(updateArtistApplicationRequest.getLocationType()));
        artistApplicationEntity.setSector(Sector.valueOf(updateArtistApplicationRequest.getSector()));
        artistApplicationEntity.setCopyrightName(updateArtistApplicationRequest.getCopyrightName());
        artistApplicationEntity.setAlbumName(updateArtistApplicationRequest.getAlbumName());
        artistApplicationEntity.setArtistName(updateArtistApplicationRequest.getArtistName());
        artistApplicationEntity.setAuthorName(updateArtistApplicationRequest.getAuthorName());

        artistApplicationRepository.save(artistApplicationEntity);
    }

    @Transactional
    @Override
    public void deleteArtistApplication(Long artistApplicationId) {
        ArtistApplicationEntity artistApplicationEntity = artistApplicationRepository.findById(artistApplicationId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.NOT_FOUND, HttpStatus.NOT_FOUND));
        validAuthByMemberId(artistApplicationEntity.getMember().getMemberId());
        artistApplicationEntity.setStatus(ArtistApplicationStatus.Deleted);
        artistApplicationRepository.save(artistApplicationEntity);
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

    private MemberEntity getMemberEntityByAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return memberRepository.findById(Long.parseLong(AES256Util.decrypt(authentication.getName())))
                .orElseThrow(() -> new BusinessException(ApiResponseCode.UNAUTHORIZED_ACCESS, HttpStatus.FORBIDDEN));
    }

    private void validAuthByMemberId(Long memberId) {
        if (!getMemberEntityByAuthentication().getMemberId().equals(memberId))
            throw new BusinessException(ApiResponseCode.UNAUTHORIZED_ACCESS, HttpStatus.FORBIDDEN);
    }
}